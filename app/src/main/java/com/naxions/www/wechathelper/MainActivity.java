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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.threekilogram.objectbus.bus.ObjectBus;
import com.wang.avi.AVLoadingIndicatorView;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private String USERNAME = "userName";
    private String LASTUPDATETIME = "lastUpdateTime";
    private String USERINFO = "userInfo";

    private SharedPreferences preferences;
    private static CSVPrinter contactCsvPrinter;
    private static CSVPrinter messageCsvPrinter;

    /**
     * 上传按钮
     */
    private Button btn_update;
    /**
     * 上次上传时间
     */
    private TextView tv_updateTime;
    /**
     * 正在上传提示的 loadingView
     */
    private CustomDialog loadingDialog;
    /**
     * 提示关闭微信的loadingView
     */
    private CustomDialog closeWxDialog;
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
     * 姓名输入框
     */
    private EditText et_name;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 点击上传按钮是记录的当前时间
     */
    private String currentTime;
    /**
     * 格式化后的上次上传时间
     */
    private String lastUpdateTime;
    /**
     * 上次上传时间的时间戳
     */
    private Long longLastUpdateTime;
    /**
     * 文件上传
     */
    private File file1;
    private File file2;
    
    /**
     * 点击上传按钮时的时时间戳
     */
    private String mTimeStamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        initView();
        initData();
    }

    private void initView() {
        btn_update = findViewById(R.id.btn_update);
        tv_updateTime = findViewById(R.id.tv_updateTime);
        et_name = findViewById(R.id.et_name);
        btn_update.setOnClickListener(this);

    }

    private void initData() {
        // sp中获取销售代表名字和上次上传时间
        preferences = getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
        userName = preferences.getString(USERNAME, "");
        lastUpdateTime = preferences.getString(LASTUPDATETIME, "");
        //赋值
        et_name.setText(userName.toCharArray(), 0, userName.length());
        longLastUpdateTime = Long.valueOf(DateUtil.date2Timestamp(lastUpdateTime));
        tv_updateTime.setText(lastUpdateTime);
        //获取上次上传的时间
        if (lastUpdateTime.equals("")||lastUpdateTime.equals("0")) {
            //既没有微信号也没有上次上传时间(第一次安装),将时间重置为0
            tv_updateTime.setText("暂无上传时间");
            longLastUpdateTime=Long.parseLong("0") ;
        } else {
            //有上次上传时间,赋值并记录
            tv_updateTime.setText(lastUpdateTime);
            longLastUpdateTime = Long.valueOf(DateUtil.date2Timestamp(lastUpdateTime));
            Log.e("query sp中保存的上次上传时间", DateUtil.timeStamp2Date(longLastUpdateTime+""));
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_update):
                Log.e("query================","=========");
                //判断姓名是否为空
                if (et_name.getText().toString().trim().equals("")) {
                    Toast.makeText(mActivity, "请先输入您的微信号!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存姓名
                if (preferences == null) {
                    preferences = getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
                }
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString(USERNAME, et_name.getText().toString().trim());
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



                        //点击上传文件
                        @Override
                        public void onClick(View view) {
                            //可能修改过 userName;
                            userName = et_name.getText().toString().trim();

                            //显示 loadingView
                            if (loadingDialog == null) {
                                loadingDialog = new CustomDialog(mActivity, R.style.customDialog, R.layout.layout_loading_dialog);
                            }
                            loadingDialog.setCancelable(false);
                            closeWxDialog.dismiss();
                            loadingDialog.show();
                            loadingView = loadingDialog.findViewById(R.id.loadingView);
                            mRemindText = loadingDialog.findViewById(R.id.text);
                            iv_success = loadingDialog.findViewById(R.id.iv_success);
                            iv_fail = loadingDialog.findViewById(R.id.iv_fail);
                            mRemindText.setText("正在从微信中导出聊天记录,请稍候");
                            loadingView.setVisibility(View.VISIBLE);
                            iv_success.setVisibility(View.INVISIBLE);
                            iv_fail.setVisibility(View.INVISIBLE);

                            // 请求网络获取上一次的上传时间,创建网络处理的对象
                            OkHttpClient client = new OkHttpClient.Builder()
                                    .readTimeout(50, TimeUnit.SECONDS)
                                    .build();

                            //创建一个请求对象，传入URL地址和相关数据的键值对的对象
                            Request request = new Request.Builder()
                                    .url(baseUrl+"upload/time/get/"+userName)
                                    .get().build();

                            //创建一个能处理请求数据的操作类
                            Call call = client.newCall(request);

                            //使用异步任务的模式请求数据
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, final IOException e) {

                                    Log.e("query获取上次的上传时间的 错误信息",e.toString());

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //请求失败直接不让上传,显示失败
                                            getUploadTimeError("你无法获取上传时间,请联系开发人员");
                                            Toast.makeText(mActivity,e.toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    String time = response.body().string();
                                    Log.e("query 获取上次的上传时间==", time);
                                    try {
                                        JSONObject jsonOtimebject = new JSONObject(time);
                                        Object code = jsonOtimebject.get("code");
                                        final Object description = jsonOtimebject.get("description");
                                      //  Log.e("query 获取上次的上传时间的code", code.toString());
                                        if (code.toString().equals("200")){
                                            Object data = jsonOtimebject.get("data");
                                            JSONObject jsondata = new JSONObject(data.toString());
                                            final String messageUploadTime = jsondata.getString("messageUploadTime");
                                            Log.e("query 获取上次的上传时间==", messageUploadTime);

                                            //返回的时间不为空再复制,保存,不然就不处理,直接获取sp 的时间
                                            if(!messageUploadTime.equals("")&&!messageUploadTime.equals("0")){
                                                //赋值上次上传时间
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        tv_updateTime.setText(messageUploadTime);
                                                        longLastUpdateTime = Long.valueOf(DateUtil.date2Timestamp(messageUploadTime));
                                                        //保存到 sp
                                                        if (preferences == null) {
                                                            preferences = getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
                                                        }
                                                        SharedPreferences.Editor edit = preferences.edit();
                                                        edit.putString(LASTUPDATETIME, messageUploadTime);
                                                        edit.commit();
                                                        go2GetData();
                                                    }
                                                });
                                            } else{
                                                go2GetData();
                                            }

                                        }else{
                                            getUploadTimeError("你无法获取上传时间,请联系开发人员");
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(mActivity,description.toString(),Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            });
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    Toast.makeText(mActivity, "请先安装微信", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            default:
                break;
        }
    }

    /**
     * 请求失败的弹窗处理
     * @param s
     */
    private void getUploadTimeError(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.setCancelable(true);
                mRemindText.setText(s);
                loadingView.setVisibility(View.INVISIBLE);
                iv_success.setVisibility(View.INVISIBLE);
                iv_fail.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 获取当前时间并一步执行拷贝解密操作
     */
    private void go2GetData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //获取当前时间,并转换格式
                mTimeStamp = DateUtil.getTimeStamp();
                currentTime = DateUtil.timeStamp2Date(mTimeStamp);
                Log.e("query当前时间值",DateUtil.timeStamp2Date(mTimeStamp));
                //异步执行文件拷贝和数据查询操作,防止 dialog 不显示
                new MyTask().execute();
            }
        });
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
           // loadingDialog.dismiss();
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
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
    public void openWxDb(File dbFile, final Context mContext, String mDbPassword) {
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
    private void runRecontact(final Context mContext, final SQLiteDatabase db) {
        task.toPool(new Runnable() {
            @Override
            public void run() {
                getRecontactDate(db, mContext);
            }
        }).toMain(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(mContext, "文件导出完毕完毕", Toast.LENGTH_LONG).show();
            }
        }).run();
    }


    /**
     * 获取当前用户的微信所有联系人
     */
    private void getRecontactDate(SQLiteDatabase db, Context mContext) {
        Cursor c1 = null;
        Cursor c2 = null;
        try {
            //新建文件保存联系人信息
            file1 = new File(Environment.getExternalStorageDirectory().getPath() + "/" + et_name.getText().toString().trim() + "_contact_file" + ".csv");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "UTF-8"));
            contactCsvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("userName", "nickName", "alias", "conRemark", "type"));
            //新建文件保存聊天记录
            file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/" + et_name.getText().toString().trim() + "_message_file" + ".csv");
            BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "UTF-8"));  // 防止出现乱码
            messageCsvPrinter = new CSVPrinter(writer2, CSVFormat.DEFAULT.withHeader("talker", "content", "createTime", "imgPath", "isSend", "type"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 查询所有联系人verifyFlag!=0:公众号等类型，群里面非好友的类型为4，未知类型2）
            c1 = db.rawQuery(
                    "select * from rcontact where verifyFlag = 0 and type != 4 and type != 2 and type != 0 and type != 33 and nickname != ''and nickname != '文件传输助手'",
                    null);
            while (c1.moveToNext()) {
                String userName = c1.getString(c1.getColumnIndex("username"));
                String nickName = c1.getString(c1.getColumnIndex("nickname"));
                String alias = c1.getString(c1.getColumnIndex("alias"));
                String conRemark = c1.getString(c1.getColumnIndex("conRemark"));
                String type = c1.getString(c1.getColumnIndex("type"));
                boolean b = checkNickName(conRemark);
                if(b){
                    Log.e("contact", "userName=" + userName + "nickName=" + nickName + "alias=" + alias + "conRemark=" + conRemark + "type=" + type);
                    //将联系人信息写入 csv 文件
                    contactCsvPrinter.printRecord(userName, nickName, alias, conRemark, type);
                }

            }
            contactCsvPrinter.printRecord();
            contactCsvPrinter.flush();

            //查询聊天记录
            String query = "select * from message where  createTime > "+longLastUpdateTime;
           // String query = "select * from message where  createTime > 0";
            Log.e("query查询分割时间",DateUtil.timeStamp2Date(longLastUpdateTime+""));

            c2 = db.rawQuery(query, null);
            while (c2.moveToNext()) {
                String content = c2.getString(c2.getColumnIndex("content"));
                String talker = c2.getString(c2.getColumnIndex("talker"));
                String createTime = c2.getString(c2.getColumnIndex("createTime"));
                int isSend = c2.getInt(c2.getColumnIndex("isSend"));
                int imgPath = c2.getInt(c2.getColumnIndex("imgPath"));
                int type = c2.getInt(c2.getColumnIndex("type"));
                //Log.e(longLastUpdateTime+"",createTime);
                if(content!=null){
                    Log.e("chatInfo", "talker=" + talker + "createTime=" + DateUtil.timeStamp2Date(createTime.toString()) + "content=" + content + "imgPath=" + imgPath + "isSend=" + isSend + "type=" + type);
                    //将聊天记录写入 csv 文件
                    String messageType;
                    switch (type) {
                        case 1:
                            messageType = "文字消息";
                            break;
                        case 47:
                            messageType = "表情消息";
                            break;
                        case 43:
                            messageType = "视频消息";
                            break;
                        case 49:
                            messageType = "链接/小程序/聊天记录";
                            break;
                        case 50:
                            messageType = "语音视频通话";
                            break;
                        case 3:
                            messageType = "图片消息";
                            break;
                        case 34:
                            messageType = "语音消息";
                            break;
                        case 48:
                            messageType = "地图消息";
                            break;
                        case 10000:
                            messageType = "撤回提醒";
                            break;
                        default:
                            messageType = "其他消息";
                            break;

                    }
                    messageCsvPrinter.printRecord(talker, EmojiFilter.filterEmoji(content), DateUtil.timeStamp2Date(createTime.toString()), imgPath, isSend, messageType);

                }
            }
            messageCsvPrinter.printRecord();
            messageCsvPrinter.flush();

            c1.close();
            c2.close();
            db.close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRemindText.setText("正在向工作台上传聊天记录,请稍候");
                }
            });

            //上传联系人
            upLoadFiles(baseUrl+"contact/import?uploadTime="+currentTime,file1,et_name.getText().toString().trim() + "_contact_file.cvs",false);
            //上传聊天记录
            upLoadFiles(baseUrl+"message/import?uploadTime="+currentTime, file2,et_name.getText().toString().trim() + "_message_file.cvs",true);

        } catch (Exception e) {
            c1.close();
            c2.close();
            db.close();
            Log.e("openWxDb", "读取数据库信息失败" + e.toString());
        }
    }

    /**
     * @param url
     * @throws Exception
     * isSave 用来表示只有消息表上传成功时,才保存上传时间到 sp
     */
    private void upLoadFiles(String url, File file , String name, final boolean isSave) {
        Log.e("query网站", url+file.getName());
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (file.exists()) {

            String TYPE = "application/octet-stream";
            RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);

            RequestBody requestBody = builder
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), fileBody)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("query上传文件的返回错误", e.toString());
                    //上传失败
                    if(isSave){
                        getUploadTimeError("聊天记录上传失败请联系开发人员");
                    } else{
                        getUploadTimeError("联系人上传失败请联系开发人员");
                    }


                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    Log.e("query上传文件的返回值", string);
                    try {
                        JSONObject objects = new JSONObject(string);
                        Object code = objects.get("code");
                        Log.e("query上传文件的code", code.toString());
                        if(code.toString().equals("200")){
                            //上传成功,重新赋值时间并保存sp是
                            if(isSave){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingDialog.setCancelable(true);
                                        loadingView.setVisibility(View.INVISIBLE);
                                        iv_fail.setVisibility(View.INVISIBLE);
                                        iv_success.setVisibility(View.VISIBLE);
                                        mRemindText.setText("上传成功");
                                        tv_updateTime.setText(currentTime);
                                        if (preferences == null) {
                                            preferences = getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
                                        }
                                        SharedPreferences.Editor edit = preferences.edit();
                                        edit.putString(LASTUPDATETIME, currentTime);
                                        edit.commit();
                                        //重新赋值本次上传时间
                                        longLastUpdateTime = Long.valueOf(mTimeStamp);
                                        Log.e("query聊天记录上传成功后更新的时间", DateUtil.timeStamp2Date(longLastUpdateTime + ""));
                                    }
                                });
                            }else {
                                Log.e("query联系人上传成功","==");
                            }

                        }else{

                            if(isSave){
                                getUploadTimeError("聊天记录上传失败请联系开发人员");
                            } else{
                                getUploadTimeError("联系人上传失败请联系开发人员");
                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
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

    /**
     * 过滤联系人,只获取备注有电话号码的联系人并写入 csv 上传
     * @param nickName
     * @return
     */
    public static boolean checkNickName(String nickName) {

        // 过滤出纯数字
        nickName = Pattern.compile("[^0-9]").matcher(nickName.trim()).replaceAll("");
        if (nickName.length() < 11) {
            return false;
        }
        char[] chars = nickName.toCharArray();
        ArrayList<String> phoneList = new ArrayList<>();//所有11位数字的集合
        for(int i = 0; i < chars.length; i++){
            StringBuilder stringBuilder = new StringBuilder();
            for(int j = 0; j < 11; j++){
                if(i + j < chars.length){
                    stringBuilder.append(chars[i + j]);
                }
            }
            if(stringBuilder.length()==11){
                phoneList.add(stringBuilder.toString());
            }
        }



        List<String> regexList = new ArrayList<>();
        /**
         * 手机号码
         * 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
         * 联通：130,131,132,152,155,156,185,186
         * 电信：133,1349,153,180,189,181(增加)
         */
        regexList.add("^1(3[0-9]|5[0-35-9]|8[025-9])\\d{8}$");
        /**
         * 中国移动：China Mobile
         * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
         */
        regexList.add("^1(34[0-8]|(3[5-9]|5[017-9]|8[2378])\\d)\\d{7}$");
        /**
         * 中国联通：China Unicom
         * 130,131,132,152,155,156,185,186
         */
        regexList.add("^1(3[0-2]|5[256]|8[56])\\d{8}$");
        /**
         * 中国电信：China Telecom
         * 133,1349,153,180,189,181(增加)
         */
        regexList.add("^1((33|53|8[019])[0-9]|349)\\d{7}$");
        for(String phone : phoneList){
            for (String regex : regexList) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(phone);
                if (matcher.matches()) {
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

