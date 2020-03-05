package com.jack.isafety;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jack.specialEffects.StringUtils;
import com.jack.sqlite.DBUtils;
import com.jack.sqlite.UserBean;
import com.maintabs_d_secondpages.UserActivity;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import es.dmoral.toasty.Toasty;
import io.socket.emitter.Emitter;
import xin.skingorz.Bean.User;
import xin.skingorz.internet.Search;
import xin.skingorz.internet.UpdataUser;
import xin.skingorz.utils.GlobalVariable;


import static com.jack.service.BaseService.mSocket;
import static com.jack.sqlite.UserBean.Friend;
import static com.jack.sqlite.UserBean.home;
import static com.jack.sqlite.UserBean.id;
import static com.jack.sqlite.UserBean.location;
import static com.jack.sqlite.UserBean.messageText;
import static com.jack.sqlite.UserBean.currentLocation;
import static com.jack.sqlite.UserBean.phone;
import static com.jack.sqlite.UserBean.phoneNumber;
import static com.jack.sqlite.UserBean.sex;
import static com.jack.sqlite.UserBean.signature;
import static com.jack.sqlite.UserBean.userName;

public class Maintabs_AActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{



    protected Context mContext;

    //相机相关
    public static final int VIDEO_REQUEST = 0;// 录像
    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    public static final int CROP_PHOTO = 2;  //相册

    //照片显示区域
    //private ImageView picture;

    private Uri imageUri;
    public static File tempFile;


    private UpdataUser updataUser=new UpdataUser();

    //小功能部件
    private LinearLayout Header_Dial;
    private LinearLayout Header_Message;
    private LinearLayout Header_takePhoto;
    private LinearLayout Header_Video;

    private TextView Header_Show;
    private TextView Body_Show;

    private JSONArray jsonArray;


    String email;

    String rUsername;
    String spUserName=id;
    //获取信息
    //Search search=new Search();

    public Maintabs_AActivity() throws InterruptedException, JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs__a);


        mContext=this;
        //初始化部件
        initView();

        //picture = (ImageView) findViewById(R.id.picture);

        //初始化数据
        initDate();

        Map<String,String> map=new HashMap<String, String>();
        map.put("search",userName);

        net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(map);

        mSocket.emit("getInfor",jsonObject);

        mSocket.on("searchResult",getInformation);

        //录像与相机点击按钮
        Header_takePhoto.setOnClickListener(this);
        Header_Video.setOnClickListener(this);

        //设置点击
        Header_Dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(phoneNumber);
            }
        });

        Header_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message(phoneNumber,messageText,currentLocation);
            }
        });

        Header_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        Body_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Maintabs_AActivity.this, "请选择您的守护者", Toast.LENGTH_SHORT).show();
            }
        });


        //长按
        Header_Dial.setOnLongClickListener(this);//注册监听
        Header_Message.setOnLongClickListener(this);//注册监听


        /**listview相关*/

        int[] Listview_item_logo = new int[]{
                R.mipmap.safergo/*, R.drawable.protect, R.color.main_color_darkblue, R.color.main_color_darkgreen, R.color.main_color_red, R.color.main_color_pink,
*/
        };
        String[] Listview_item_title=new String[]{
                "jack"
        };
        String[] Listview_item_email = new String[]{
                "2508074836@qq.com"
        };



        List<Map<String,Object>> List_item=new ArrayList<Map<String,Object>>();
        for(int i=0;i<Listview_item_logo.length;i++){
            Map<String,Object> map1=new HashMap<String, Object>();
            map1.put("main_listview_item_logo",Listview_item_logo[i]);
            map1.put("main_listview_item_title",Listview_item_title[i]);
            map1.put("main_listview_item_email",Listview_item_email[i]);
            List_item.add(map1);
        }

        //配置适配器
        SimpleAdapter adapter=new SimpleAdapter(this,List_item, R.layout.maintabs_a_listview_item,new String[]{"main_listview_item_logo","main_listview_item_title","main_listview_item_email"},
                new int[]{R.id.main_listview_item_logo, R.id.main_listview_item_title,R.id.main_listview_item_id});
        ListView mMain_listview=findViewById(R.id.maintabs_a_listview);
        mMain_listview.setAdapter(adapter);

        //监听点击
        mMain_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) parent.getItemAtPosition(position);
                Toast.makeText(Maintabs_AActivity.this, map.get("main_listview_item_title").toString(), Toast.LENGTH_SHORT).show();

                //onBackPressed();

                /*switch (position){
                    case 0:

                        break;
                }*/

            }
        });


        /*mSocket.emit("GetFriend");

        mSocket.on("allFriends",FriendLister);


        for (int i = 0; i < Friend.length(); i++) {
            //Listview_item_title = new String[userArrary.length];
            //Listview_item_title[i] = userArrary[i].getUsername();

            try {
                Listview_item_email[i]= ((JSONObject) UserBean.Friend.get(i)).getString("friends_email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //listview_item的添加
        //素材数组的构建

        List<Map<String, Object>> List_item = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Friend.length(); i++) {
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("main_listview_item_logo", Listview_item_logo[i]);
            //map.put("main_listview_item_title", userArrary[i].getUsername());
            map2.put("main_listview_item_email", Listview_item_email[i]);
            List_item.add(map2);
        }

        //配置适配器
        SimpleAdapter adapter = new SimpleAdapter(this, List_item, R.layout.maintabs_a_listview_item, new String[]{"main_listview_item_logo", *//*"main_listview_item_title", *//*"main_listview_item_email"},
                new int[]{R.id.main_listview_item_logo, *//*R.id.main_listview_item_title,*//* R.id.main_listview_item_id});
        ListView mMain_listview = findViewById(R.id.maintabs_a_listview);
        mMain_listview.setAdapter(adapter);

        //监听点击
        mMain_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                Toasty.info(Maintabs_AActivity.this,  "好友守护", Toast.LENGTH_SHORT, true).show();
                *//*Intent intent=new Intent(Maintabs_AActivity.this, com.elabs.android.chatview.MainActivity.class);
                startActivity(intent);*//*

                *//*for(int i=0;i<Friend.length();i++){

                    if (position)
                    position=i;
                    //String name=Friend[i].getUsername();
                    String email=Listview_item_email[i];
                    Intent intent=new Intent(Maintabs_AActivity.this,com.elabs.android.chatview.MainActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("name",name);
                    bundle.putString("email",email);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }*//*
                switch (position) {
                    case 0:
                        //GlobalVariable.handle.sendPeople(userArrary[0].getEmail());
                        break;
                }

            }
        });*/



       /** 悬浮按钮*/

        BoomMenuButton bmb = findViewById(R.id.bmb_a);

        HamButton.Builder builder = new HamButton.Builder()
                .normalImageRes(R.drawable.maintabs_a_addperson)
                .normalText("添加好友或群组！")
                .imageRect(new Rect(35, 30, Util.dp2px(50), Util.dp2px(50)))
                .subNormalText("好友多多，守护多多～")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Intent intent = new Intent(Maintabs_AActivity.this, com.maintabs_secondpages.Maintabs_a_adduser.class);
                        startActivity(intent);
                    }
                });

        bmb.addBuilder(builder);

        HamButton.Builder builder3 = new HamButton.Builder()
                .normalImageRes(R.drawable.maintabs_a_search)
                .imageRect(new Rect(35, 30, Util.dp2px(50), Util.dp2px(50)))
                .normalText("查询好友或群组！")
                .subNormalText("快速查找自己的守护对象～")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Intent intent = new Intent(Maintabs_AActivity.this, com.maintabs_secondpages.Maintabs_a_searchuser.class);
                        startActivity(intent);
                    }
                });
        bmb.addBuilder(builder3);

        HamButton.Builder builder2 = new HamButton.Builder()
                .normalImageRes(R.drawable.maintabs_a_addgroup)
                .imageRect(new Rect(35, 30, Util.dp2px(50), Util.dp2px(50)))
                .normalText("交际圈！")
                .subNormalText("发现SaferGo更多有趣内容～")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Intent intent = new Intent(Maintabs_AActivity.this, com.maintabs_secondpages.Maintabs_a_group.class);
                        startActivity(intent);
                    }
                });
        bmb.addBuilder(builder2);

        HamButton.Builder builder4 = new HamButton.Builder()
                .normalImageRes(R.drawable.maintabs_a_share)
                .imageRect(new Rect(35, 30, Util.dp2px(50), Util.dp2px(50)))
                .normalText("分享守护！")
                .subNormalText("让更多的人来守护你这个小可爱吧～")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Toast.makeText(Maintabs_AActivity.this, "SaferGo", Toast.LENGTH_SHORT).show();
                    }
                });
        bmb.addBuilder(builder4);



    }



    private Emitter.Listener FriendLister = new Emitter.Listener() {


        @Override
        public void call(Object... args) {
            //主线程调用
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = null;
                    try {
                        data = new JSONObject((String)args[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    try {
                        /*phoneNumber ="";
                        messageText = "SaferGo官方短信：您的好友发生突发情况，需要您的帮助!";
                        currentLocation = "发生情况位置为：北京市朝阳区";*/

                        Friend= data.getJSONArray("friend");

                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };

    private Emitter.Listener getInformation = new Emitter.Listener() {


        @Override
        public void call(Object... args) {
            //主线程调用
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = null;
                    try {
                        data = new JSONObject((String)args[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    try {
                        /*phoneNumber ="";
                        messageText = "SaferGo官方短信：您的好友发生突发情况，需要您的帮助!";
                        currentLocation = "发生情况位置为：北京市朝阳区";*/

                        phoneNumber = data.getString("emergencyPhone");
                        messageText = data.getString("msgTemplate");



                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };

    //点击重写
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.maintabs_a_body_header_4: {
                openCamera(this);
                break;
            }
            case R.id.maintabs_a_body_header_3: {
                openVideo(this);
                break;
            }

        }
    }


    //部件初始化
    private void initView() {

        Header_Dial = findViewById(R.id.maintabs_a_body_header_1);
        Header_Message = findViewById(R.id.maintabs_a_body_header_2);
        Header_takePhoto = findViewById(R.id.maintabs_a_body_header_4);
        Header_Video=findViewById(R.id.maintabs_a_body_header_3);
        Header_Show=findViewById(R.id.miantabs_a_body_body_show);
        Body_Show=findViewById(R.id.miantabs_a_body_body_show2);

    }


    //初始化数据
    private void initDate() {
        UserBean bean = null;
        //实例化DBUtils，同时调用其方法获取个人信息资料
        //bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        bean = DBUtils.getInstance(this).getUserInfo("username");
        //如果第一次进入，数据库没有保留用户信息
        if (bean == null) {
            bean = new UserBean();
            bean.userName = "name";
            bean.messageText = "SaferGo官方短信：您的好友发生突发情况，需要您的帮助!";
            bean.currentLocation = "发生情况位置为：北京市朝阳区";

            //保存到数据库
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        //setValue(bean);
    }

    //打电话
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    //发短信
    private void message(String phone,String contents1,String contents2){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);             //设置动作为发送短信
        intent.setData(Uri.parse("smsto:"+phone));           //设置发送的号码
        intent.putExtra("sms_body",messageText+contents1+contents2);           //设置发送的内容
        Toast.makeText(Maintabs_AActivity.this, "跳转成功", Toast.LENGTH_SHORT).show();
        startActivity(intent);                               //启动 Activity
    }


    //请求码反馈
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA://调用相机
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case VIDEO_REQUEST://调用录像
                if (resultCode == RESULT_OK) {

                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "video/*");
                    //intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    //startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                }
                break;
                /* case CROP_PHOTO://调用相册
                if (resultCode == RESULT_OK) {
                    try {
                        System.out.println(data.toString().length());
                        if (data.toString().length() != 11) {
                            Uri uri = data.getData();
                            imageUri = uri;
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break; */
        }
    }

    //打开相机
    public void openCamera(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }


    //打开录像
    public void openVideo(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                    filename + ".mp4");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                //ContentValues contentValues = new ContentValues(1);
                //contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                //imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, VIDEO_REQUEST);
    }


    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }



    //调用相册
/*    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CROP_PHOTO);
    }*/

        //   GlobalVariable.handle.sendPeople();


    private void showDialog() {

        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_maintabs_a_show,null);
        mAlertDialog.setContentView(view);

        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                mAlertDialog.cancel();
            }
        });
        Window window = mAlertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    @Override
    public boolean onLongClick(View v) {

        if(v == Header_Dial){//当按下的是按钮时
            showPhoneDialog();
        }
        if(v==Header_Message){
            showMessageDialog();
        }
        return false;
    }

    /**
     * 显示更换电话对话框
     */
    public void showPhoneDialog() {

        //从服务器获取电话
        //String phone = currentUser.getMobilePhoneNumber();

        new MaterialDialog.Builder(mContext)
                .title("好友电话")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(0, 11, R.color.red)
                .positiveColor(getResources().getColor(R.color.white))
                .negativeColor(getResources().getColor(R.color.white))
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .titleColor(getResources().getColor(R.color.white))
                .contentColor(getResources().getColor(R.color.white))
                .input("请输入预设好友电话号码", phoneNumber, (dialog, input) -> {
                    String inputStr=input.toString();
                    if (inputStr.equals("")) {
                        Toast.makeText(Maintabs_AActivity.this,
                                "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                    } else {
                        if (StringUtils.checkPhoneNumber(inputStr)) {

                            //上传服务器
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("emergencyPhone",inputStr);

                            net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(map);

                            mSocket.emit("SetInfor",jsonObject);


                            phoneNumber=inputStr;
                            setPhoneNumber(phoneNumber);


                        } else {
                            Toast.makeText(Maintabs_AActivity.this,
                                    "请输入正确的电话号码", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .positiveText("确定")
                .show();
    }


    /**
     * 显示消息模板对话框
     */
    public void showMessageDialog() {

        new MaterialDialog.Builder(mContext)
                .title("消息模板")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(0, 80, R.color.red)
                .positiveColor(getResources().getColor(R.color.white))
                .negativeColor(getResources().getColor(R.color.white))
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .titleColor(getResources().getColor(R.color.white))
                .contentColor(getResources().getColor(R.color.white))
                .input("请输入消息模板", messageText, (dialog, input) -> {
                    String inputStr=input.toString();
                    if (inputStr.equals("")) {
                        Toast.makeText(Maintabs_AActivity.this,
                                "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                    } else {

                        /*设置上传服务器*/
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("msgTemplate",inputStr);

                        net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(map);

                        mSocket.emit("SetInfor",jsonObject);

                        messageText=inputStr;
                        setMessageText(messageText);
                    }
                })
                .positiveText("确定")
                .show();
    }

    private void setPhoneNumber(String Number) {

        //更新数据库字段
        DBUtils.getInstance(this).updateUserInfo("phoneNumber",Number, spUserName);

    }

    private void setMessageText(String Number) {

        //更新数据库字段
        DBUtils.getInstance(this).updateUserInfo("messageText",Number, spUserName);

    }
}

