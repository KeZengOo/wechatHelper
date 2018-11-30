package com.naxions.www.wechathelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.threekilogram.objectbus.bus.ObjectBus;
import com.wang.avi.AVLoadingIndicatorView;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity mActivity;

    String WXPackageName = "com.tencent.mm";

    private static final ObjectBus task = com.threekilogram.objectbus.bus.ObjectBus.newList();

    //微信数据库路径
    public final String WX_ROOT_PATH = "/data/data/com.tencent.mm/";
    private final String WX_DB_DIR_PATH = WX_ROOT_PATH + "MicroMsg";
    private final String WX_DB_FILE_NAME = "EnMicroMsg.db";

    //拷贝到sd 卡的路径
    private String mCcopyPath = Environment.getExternalStorageDirectory().getPath() + "/";
    private final String COPY_WX_DATA_DB = "wx_data.db";
    String copyFilePath = mCcopyPath + COPY_WX_DATA_DB;

    private SharedPreferences preferences;
    private static CSVPrinter contactCsvPrinter;
    private static CSVPrinter messageCsvPrinter;

    private Button tv_update;
    private TextView tv_updateTime;

    private CustomDialog loadingDialog;
    private CustomDialog closeWxDialog;

    private TextView mRemindText;
    private EditText et_name;
    private String userName;
    private AVLoadingIndicatorView loadingView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        initView();
        initData();
    }

    private void initView() {
        tv_update = findViewById(R.id.tv_update);
        tv_updateTime = findViewById(R.id.tv_updateTime);
        et_name = findViewById(R.id.et_name);
        tv_update.setOnClickListener(this);

    }

    private void initData() {
        //获取代表名字
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName = preferences.getString("userName", "");
        et_name.setText(userName.toCharArray(), 0, userName.length());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.tv_update):

                //判断姓名是否为空
                if (et_name.getText().toString().trim().equals("")) {
                    Toast.makeText(mActivity, "请先输入您的姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存姓名
                if (preferences == null) {
                    preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                }
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("userName", et_name.getText().toString().trim());
                edit.commit();

                //判断是否安装了微信
                if (isWeixinAvilible()) {
                    if (closeWxDialog == null) {
                        closeWxDialog = new CustomDialog(this, R.style.customDialog, R.layout.layout_closewx_dialog);
                    }
                    closeWxDialog.show();
                    TextView tv_updateData = closeWxDialog.findViewById(R.id.tv_updateData);
                    TextView tv_closeWX = closeWxDialog.findViewById(R.id.tv_closeWX);
                    tv_closeWX.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //跳转到微信设置界面,关闭微信,避免操作同一数据库崩溃冲突
                            Uri packageURI = Uri.parse("package:" + WXPackageName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    });
                    tv_updateData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //异步执行文件拷贝和数据查询操作,防止 dialog 不显示
                            new MyTask().execute();

                        }
                    });
                } else {
                    Toast.makeText(mActivity, "请先安装微信", Toast.LENGTH_SHORT).show();
                }

            default:
                break;
        }
    }

    public class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //拷贝前先提示正在处理
            super.onPreExecute();
            if (loadingDialog == null) {
                loadingDialog = new CustomDialog(mActivity, R.style.customDialog, R.layout.layout_loading_dialog);
            }
            loadingDialog.setCancelable(false);
            closeWxDialog.dismiss();
            loadingDialog.show();
            loadingView = loadingDialog.findViewById(R.id.loadingView);
            mRemindText = loadingDialog.findViewById(R.id.text);
        }

        @Override
        protected String doInBackground(String... strings) {
            //获取root权限
            passwordUtiles.execRootCmd("chmod 777 -R " + WX_ROOT_PATH);
            //获取root权限
            passwordUtiles.execRootCmd("chmod 777 -R " + copyFilePath);

            String password = passwordUtiles.initDbPassword(mActivity);
            String uid = passwordUtiles.initCurrWxUin();
            try {
                String path = WX_DB_DIR_PATH + "/" + Md5Utils.md5Encode("mm" + uid) + "/" + WX_DB_FILE_NAME;
                Log.e("path", copyFilePath);
                Log.e("path===", path);
                Log.e("path", password);
                //微信原始数据库的地址
                File wxDataDir = new File(path);

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                });

                //将微信数据库拷贝出来，因为直接连接微信的db，会导致微信崩溃
                copyFile(wxDataDir.getAbsolutePath(), copyFilePath);
                //将微信数据库导出到sd卡操作sd卡上数据库
                openWxDb(new File(copyFilePath), mActivity, password);
            } catch (Exception e) {
                Log.e("path", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //完成后消失
            loadingDialog.dismiss();
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public  void copyFile(String oldPath, String newPath) {
        try {
            int byteRead = 0;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            Log.e("copyFile", "复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 连接数据库
     */
    public  void openWxDb(File dbFile, final Context mContext, String mDbPassword) {
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
        runRecontact(mContext, db);
    }


    /**
     * 微信好友信息
     *
     * @param mContext
     * @param db
     */
    private  void runRecontact(final Context mContext, final SQLiteDatabase db) {
        task.toPool(new Runnable() {
            @Override
            public void run() {
                getRecontactDate(db,mContext);
            }
        }).toMain(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "文件导出完毕完毕", Toast.LENGTH_LONG).show();
            }
        }).run();
    }

    /**
     * 获取当前用户的微信所有联系人
     */
    private  void getRecontactDate(SQLiteDatabase db, Context mContext) {
        Cursor c1 = null;
        Cursor c2 = null;
        try {
            //新建文件保存联系人信息
            File file1 = new File(Environment.getExternalStorageDirectory().getPath() + "/"+userName+"contact_file" + ".csv");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "UTF-8"));
            contactCsvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("userName", "nickName", "alias", "conRemark","type"));
            //新建文件保存聊天记录
            File file2= new File(Environment.getExternalStorageDirectory().getPath() + "/"+userName+"message_file" + ".csv");
            BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "UTF-8"));  // 防止出现乱码
            messageCsvPrinter = new CSVPrinter(writer2, CSVFormat.DEFAULT.withHeader("talker", "content", "createTime", "isSend"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 查询所有联系人verifyFlag!=0:公众号等类型，群里面非好友的类型为4，未知类型2）
            c1 = db.rawQuery(
                    "select * from rcontact where verifyFlag = 0 and type != 4 and type != 2 and type != 0 and type != 33 and nickname != ''",
                    null);
            while (c1.moveToNext()) {
                String userName = c1.getString(c1.getColumnIndex("username"));
                String nickName = c1.getString(c1.getColumnIndex("nickname"));
                String alias = c1.getString(c1.getColumnIndex("alias"));
                String conRemark = c1.getString(c1.getColumnIndex("conRemark"));
                String type = c1.getString(c1.getColumnIndex("type"));
                Log.e("contact", "userName=" + userName + "nickName=" + nickName + "alias=" + alias + "conRemark=" + conRemark + "type=" + type);
                //将联系人信息写入 csv 文件
                contactCsvPrinter.printRecord(userName,nickName,alias,conRemark,type);
            }
            contactCsvPrinter.printRecord();
            contactCsvPrinter.flush();

            //查询聊天记录
            c2 = db.rawQuery(
                    "select * from message where type = 1 and createTime > 1543207160000 ",
                    null);
            while (c2.moveToNext()) {
                String content = c2.getString(c2.getColumnIndex("content"));
                String talker = c2.getString(c2.getColumnIndex("talker"));
                String createTime = c2.getString(c2.getColumnIndex("createTime"));
                int isSend = c2.getInt(c2.getColumnIndex("isSend"));
                Log.e("chatInfo", "talker=" + talker + "content=" + content+ "isSend=" + isSend);
                //将聊天记录写入 csv 文件
                messageCsvPrinter.printRecord(talker,content,createTime,isSend);
            }
            messageCsvPrinter.printRecord();
            messageCsvPrinter.flush();

            c1.close();
            c2.close();
            db.close();
        } catch (Exception e) {
            c1.close();
            c2.close();
            db.close();
            Log.e("openWxDb", "读取数据库信息失败" + e.toString());
        }
    }

    /**
     * 判断是否安装了微信
     *
     * @return
     */
    public boolean isWeixinAvilible() {
        final PackageManager packageManager = mActivity.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(WXPackageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (closeWxDialog != null) {
            closeWxDialog.dismiss();
        }
    }

}

