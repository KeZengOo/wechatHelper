package com.naxions.www.wechathelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.naxions.www.wechathelper.util.FileUtil;
import com.naxions.www.wechathelper.util.FilterUtil;
import com.naxions.www.wechathelper.util.Md5Utils;
import com.naxions.www.wechathelper.util.PasswordUtiles;
import com.threekilogram.objectbus.bus.ObjectBus;
import com.wang.avi.AVLoadingIndicatorView;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * @Author: zengke
 * @Data: 2018.12
 *
 */
public class ExportActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private List<DataBean> mDatas;
    private MyAdapter mAdapter;
    private static CSVPrinter contactCsvPrinter;
    private static final ObjectBus TASK = ObjectBus.newList();
    /**
     微信数据库路径
     */
    public final String WX_ROOT_PATH = "/data/data/com.tencent.mm/";
    public final String WX_DB_DIR_PATH = WX_ROOT_PATH + "MicroMsg";
    public final String WX_DB_FILE_NAME = "EnMicroMsg.db";
    public String password = "";
    /**
    拷贝到sd 卡的路径
     */
    public String copyPath = Environment.getExternalStorageDirectory().getPath() + "/";
    public final String COPY_WX_DATA_DB = "wx_data.db";
    public final String OTHERLABEL = "otherLabel";
    String copyFilePath = copyPath + COPY_WX_DATA_DB;
    /**
     * 正在上传提示的 loadingView
     */
    private CustomDialog loadingDialog;
    /**
     * 转圈圈的图
     */
    private AVLoadingIndicatorView loadingView;
    /**
     * 正在上传的提示
     */
    private TextView mRemindText;
    /**
     * 上传成功的图标
     */
    private ImageView iv_success;
    /**
     * 上传失败的图标
     */
    private ImageView iv_fail;

    /**
     * 文件上传
     */
    private File file1;
    private Activity mActivity;
    private CheckBox selectAll;
    private TextView download;
    String labelSql="select * from ContactLabel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        if (Build.VERSION.SDK_INT >= 23) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //状态栏颜色设置透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        mActivity = this;
        listView = findViewById(R.id.listview);
        selectAll = findViewById(R.id.selectAll);
        download = findViewById(R.id.download);
        mDatas = new ArrayList<>();
        selectAll.setOnClickListener(this);
        download.setOnClickListener(this);
        //加载获取标签列表
        //显示正在获取联系人的 dialog
        if (loadingDialog == null) {
            loadingDialog = new CustomDialog(mActivity, R.style.customDialog, R.layout.layout_loading_dialog);
        }
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        loadingView = loadingDialog.findViewById(R.id.loadingView);
        mRemindText = loadingDialog.findViewById(R.id.text);
        iv_success = loadingDialog.findViewById(R.id.iv_success);
        iv_fail = loadingDialog.findViewById(R.id.iv_fail);
        mRemindText.setText("正在加载联系人标签,请稍候");
        loadingView.setVisibility(View.VISIBLE);
        iv_success.setVisibility(View.INVISIBLE);
        iv_fail.setVisibility(View.INVISIBLE);

        //异步执行文件拷贝和数据查询操作,防止 dialog 不显示
        new MyTask().execute();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectAll:
                //全选和反选
                for (int i = 0; i < mDatas.size(); i++) {
                    if (selectAll.isChecked()) {
                        mDatas.get(i).isCheck = true;
                    } else {
                        mDatas.get(i).isCheck = false;
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            //导出记录
            case R.id.download:
                //type 为2意思是导出选中的联系人
                openWxDb(new File(copyFilePath), mActivity, password, 2);
                break;
                default:
                    break;
        }
    }

    public class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //拷贝前先提示正在处理
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            //获取root权限
            PasswordUtiles.execRootCmd("chmod 777 -R " + WX_ROOT_PATH);
            //获取root权限
            PasswordUtiles.execRootCmd("chmod 777 -R " + copyFilePath);
            password = PasswordUtiles.initDbPassword(mActivity);
            String uid = PasswordUtiles.initCurrWxUin(mActivity);
            try {
                String path = WX_DB_DIR_PATH + "/" + Md5Utils.md5Encode("mm" + uid) + "/" + WX_DB_FILE_NAME;
                if(MainActivity.isDebug){
                    Log.e("path", copyFilePath);
                    Log.e("path===", path);
                    Log.e("path", password);
                }
                if (password.equals("") || password == null) {
                    getUploadTimeError("密码获取失败");
                }
                //微信原始数据库的地址
                File wxDataDir = new File(path);

                //将微信数据库拷贝出来，因为直接连接微信的db，会导致微信崩溃
                FileUtil.copyFile(wxDataDir.getAbsolutePath(), copyFilePath);
                //将微信数据库导出到sd卡操作sd卡上数据库,type 为1 的意思是读取数据库标签数据
                openWxDb(new File(copyFilePath), mActivity, password, 1);
            } catch (Exception e) {
                if(MainActivity.isDebug){
                    Log.e("path", e.getMessage());
                }
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //完成后消失
            // loadingDialog.dismiss();
        }
    }

    /**
     * 连接数据库
     */
    public void openWxDb(File dbFile, final Activity mContext, String mDbPassword, int type) {
        SQLiteDatabase.loadLibs(mContext);
        SQLiteDatabaseHook hook = new SQLiteDatabaseHook() {
            @Override
            public void preKey(SQLiteDatabase database) {
            }

            @Override
            public void postKey(SQLiteDatabase database) {
                database.rawExecSQL("PRAGMA cipher_migrate;");
            }
        };
        //打开数据库连接
        final SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, mDbPassword, null, hook);
        runRecontact(mContext, db, type);
    }

    /**
     * 微信好友信息
     *
     * @param mContext
     * @param db
     */
    public void runRecontact(final Activity mContext, final SQLiteDatabase db, final int type) {

        TASK.toPool(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case 1:
                        getContactLabel(db, mContext);
                        break;
                    case 2:
                        getRecontactData(db, mContext);
                        break;
                        default:
                            break;
                }

            }
        }).toMain(new Runnable() {
            @Override
            public void run() {
            }
        }).run();
    }

    /**
     * 获取当前用户的微信所有联系人
     */
    public void getContactLabel(SQLiteDatabase db, Context mContext) {
        Cursor c1 = null;
        try {
            // 查询所有联系人verifyFlag!=0:公众号等类型，群里面非好友的类型为4，未知类型2）
            c1 = db.rawQuery(labelSql, null);
            while (c1.moveToNext()) {
                String labelName = c1.getString(c1.getColumnIndex("labelName"));
                String labelID = c1.getString(c1.getColumnIndex("labelID"));
                mDatas.add(new DataBean(labelID, labelName));
            }
            mDatas.add(new DataBean(OTHERLABEL, "其他"));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mDatas.size() == 0) {
                        getUploadTimeError("未查询到联系人标签,请确认");
                    } else {
                        if (loadingDialog == null) {
                            loadingDialog = new CustomDialog(mActivity, R.style.customDialog, R.layout.layout_loading_dialog);
                            loadingView = loadingDialog.findViewById(R.id.loadingView);
                            mRemindText = loadingDialog.findViewById(R.id.text);
                            iv_success = loadingDialog.findViewById(R.id.iv_success);
                            iv_fail = loadingDialog.findViewById(R.id.iv_fail);
                        }
                        loadingDialog.setCancelable(true);
                        loadingView.setVisibility(View.INVISIBLE);
                        iv_fail.setVisibility(View.INVISIBLE);
                        iv_success.setVisibility(View.VISIBLE);
                        mRemindText.setText("加载联系人成功");

                        mAdapter = new MyAdapter(mActivity, mDatas);
                        listView.setAdapter(mAdapter);
                    }
                }
            });

        } catch (Exception e) {
            if(MainActivity.isDebug){
                Log.e("openWxDb", "读取数据库信息失败" + e.toString());
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getUploadTimeError("读取数据库信息失败");

                }
            });
        }finally {
            if(c1!=null){
                c1.close();
            }
            if(db!=null){
                db.close();
            }
        }
    }

    /**
     * 获取当前用户的微信所有联系人
     */
    public void getRecontactData(SQLiteDatabase db, Context mContext) {

        HashMap<String, String> integerStringMap = new HashMap<>(100);
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isCheck) {
                integerStringMap.put(mDatas.get(i).id, mDatas.get(i).desc);
                System.out.println(mDatas.get(i).id);
            }
        }
        if (integerStringMap.size() == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity, "请至少选择一个标签", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        Cursor c1;
        String name;
        try {
            //新建文件保存联系人信息
            file1 = new File(Environment.getExternalStorageDirectory().getPath() + "/联系人信息" + "_contact_file" + ".csv");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "UTF-8"));
            contactCsvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("默认微信号", "昵称", "微信号", "备注", "标签"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }

        // 查询所有联系人verifyFlag!=0:公众号等类型，群里面非好友的类型为4，未知类型2）
        c1 = db.rawQuery(
                "select * from rcontact where verifyFlag = 0 and type != 4 and type != 2 and type != 0 and type != 33 and nickname != ''and nickname != '文件传输助手'",
                null);
        while (c1.moveToNext()) {
            name = "";
            StringBuilder sb = new StringBuilder();
            String userName = c1.getString(c1.getColumnIndex("username"));
            String nickName = c1.getString(c1.getColumnIndex("nickname"));
            String alias = c1.getString(c1.getColumnIndex("alias"));
            String conRemark = c1.getString(c1.getColumnIndex("conRemark"));
            String contactLabelIds = c1.getString(c1.getColumnIndex("contactLabelIds"));
            //如果 id 为空说明没有标签,设置为默认分类
            if ("".equals(contactLabelIds)) {
                contactLabelIds = OTHERLABEL;
            }

            //先判断是否选中默认分类
            if (integerStringMap.containsKey(OTHERLABEL)) {
                //如果当前ID为OTHERLABEL,就写入
                if (contactLabelIds.equals(OTHERLABEL)) {
                    name = "其他";
                    write2CSV(name, userName, nickName, alias, conRemark, contactLabelIds);
                }
            }
            //存有多个标签,分割后相加
            if (contactLabelIds.contains(",")) {
                String[] split = contactLabelIds.split(",");
                for (String s : split) {
                    //判断每个标签是否被选中
                    if (integerStringMap.containsKey(s)) {
                        sb.append(integerStringMap.get(s) + "  ");
                        name=sb+"";
                       // name = name+integerStringMap.get(s) + "  ";
                    }
                }
                //分割后的标签可能都未被选中过,如果都没有选中,name为空,不要写入
                if (name != "") {
                    write2CSV(name, userName, nickName, alias, conRemark, contactLabelIds);
                }
            } else {
                //只有一个标签,如果存在在map里,就直接写入
                if (integerStringMap.containsKey(contactLabelIds) && !contactLabelIds.equals(OTHERLABEL)) {
                    name = integerStringMap.get(contactLabelIds);
                    write2CSV(name, userName, nickName, alias, conRemark, contactLabelIds);
                }
            }
        }
        try {
            contactCsvPrinter.printRecord();
            contactCsvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if (c1 != null) {
                c1.close();
            }

            if (db != null) {
                db.close();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog == null) {
                    loadingDialog = new CustomDialog(mActivity, R.style.customDialog, R.layout.layout_loading_dialog);
                    loadingView = loadingDialog.findViewById(R.id.loadingView);
                    mRemindText = loadingDialog.findViewById(R.id.text);
                    iv_success = loadingDialog.findViewById(R.id.iv_success);
                    iv_fail = loadingDialog.findViewById(R.id.iv_fail);
                }
                loadingDialog.setCancelable(true);
                loadingView.setVisibility(View.INVISIBLE);
                iv_fail.setVisibility(View.INVISIBLE);
                iv_success.setVisibility(View.VISIBLE);
                mRemindText.setText("导出联系人成功!");
                loadingDialog.show();
            }
        });

    }

    private void write2CSV(String name, String userName, String nickName, String alias, String conRemark, String contactLabelIds) {
        if(MainActivity.isDebug){
            if(MainActivity.isDebug){
                Log.e("contact存在", "userName=" + userName + "nickName=" + nickName + "alias=" + alias + "conRemark=" + conRemark + "contactLabelIds=" + contactLabelIds + "labelname" + name);
            }
        }
        //  将联系人信息写入 csv 文件
        try {
            contactCsvPrinter.printRecord(FilterUtil.filterEmoji(userName), FilterUtil.filterEmoji(nickName), FilterUtil.filterEmoji(alias), FilterUtil.filterEmoji(conRemark), name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求失败的弹窗处理
     *
     * @param s
     */
    private void getUploadTimeError(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog == null) {
                    loadingDialog = new CustomDialog(mActivity, R.style.customDialog, R.layout.layout_loading_dialog);
                    loadingView = loadingDialog.findViewById(R.id.loadingView);
                    mRemindText = loadingDialog.findViewById(R.id.text);
                    iv_success = loadingDialog.findViewById(R.id.iv_success);
                    iv_fail = loadingDialog.findViewById(R.id.iv_fail);
                }
                loadingDialog.setCancelable(true);
                mRemindText.setText(s);
                loadingView.setVisibility(View.INVISIBLE);
                iv_success.setVisibility(View.INVISIBLE);
                iv_fail.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 获取选中数据
     *
     * @param view
     */
    public void btnOperateList(View view) {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isCheck) {
                ids.add(mDatas.get(i).id + "");
            }
        }
        Toast.makeText(this, ids.toString(), Toast.LENGTH_SHORT).show();
        if(MainActivity.isDebug){
            if(MainActivity.isDebug){
                Log.e("TAG", ids.toString());
            }
        }
    }
}


