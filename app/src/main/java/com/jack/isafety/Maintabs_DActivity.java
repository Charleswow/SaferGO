package com.jack.isafety;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.sqlite.DBUtils;
import com.maintabs_d_secondpages.HelpActivity;
import com.maintabs_d_secondpages.WolfLightActivity;
import com.miantabs_d_share.BottomDialog;
import com.miantabs_d_share.EditTextDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import es.dmoral.toasty.Toasty;
import io.socket.emitter.Emitter;
import xin.skingorz.Bean.User;
import xin.skingorz.internet.Search;
import xin.skingorz.utils.GlobalVariable;

import static com.jack.service.BaseService.mSocket;
import static com.jack.sqlite.UserBean.email;
import static com.jack.sqlite.UserBean.id;
import static com.jack.sqlite.UserBean.userName;

@SuppressLint("SdCardPath")
public class Maintabs_DActivity extends AppCompatActivity/*implements OnClickListener*/ {

    protected Context mContext;



    private ImageView ivHead;//头像显示
    private Switch light_switch;  //手电筒开关
    private LinearLayout mSetting,mHistory,mIdsafe,mMore;
    private LinearLayout mHelp;//帮助
    private TextView mSafeBook;
    private LinearLayout mLight;//防狼手电
    private LinearLayout mMiniBook;
    private RelativeLayout mWell;


    private TextView mUsername,mUseremail;


/*  private Button btnTakephoto;//拍照
    private Button btnPhotos;//相册
    private Bitmap head;//头像Bitmap
    private static String path="/sdcard/myHead/"; //sd路径*/
    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    private ImageView imageView;
    private TextView textView;
    private File outputImagepath;//存储拍完照后的图片
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap

    private LinearLayout mRecordBook,mTicket,mRecommend,mLinks;

    private ImageView mEidtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs__d);



        mSocket.emit("userEmail","");

        mSocket.on("userEmail",getEmail);


        mContext = this;
        //imageView = (ImageView) findViewById(R.id.shangchuan_img);
        textView = (TextView) findViewById(R.id.chazhi_tv);

        ImageView btn_pic=(ImageView) findViewById(R.id.maintabs_d_header_image) ;

        LinearLayout btn_light=(LinearLayout)  findViewById(R.id.maintabs_d_body_body_1);

        btn_light.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Maintabs_DActivity.this, WolfLightActivity.class);
                startActivity(intent);
            }
        });



        mUsername=findViewById(R.id.maintabs_d_header_title);
        mUseremail=findViewById(R.id.maintabs_d_header_number);

        //mUsername.setText(userName);


        //获取信息
        /*Search search=new Search();

        JSONObject jsonObject= null;
        try {
            jsonObject = search.user(userName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String rUseremail= null;
        try {
            rUseremail = jsonObject.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/





        LinearLayout btn_call=findViewById(R.id.maintabs_d_body_body_2);

        /*按钮点击监听*/
        btn_call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Maintabs_DActivity.this,com.maintabs_d_secondpages.visual_callActivity.class  );
                startActivity(intent);
            }
        });

        LinearLayout btn_voice=findViewById(R.id.maintabs_d_body_body_3);
        btn_voice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Maintabs_DActivity.this,com.maintabs_d_secondpages.viusal_voiceActivity.class );
                startActivity(intent);
            }
        });

        LinearLayout btn_help= findViewById(R.id.maintabs_d_body_body_4);
        btn_help.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Maintabs_DActivity.this,Maintabs_BActivity.class );
                startActivity(intent);
            }
        });

        //更换头像
        /*initViewIamge();*/

        //分享按钮
        findViewById(R.id.maintabs_d_body_footer_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                /*shareDialog();*/

            }
        });

        //反馈按钮
        findViewById(R.id.maintabs_d_body_footer_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog();
                shareDialog();

            }
        });

        //监听头像的点击更换
        ivHead=findViewById(R.id.maintabs_d_header_image);
        ivHead.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                final String[] items=new String[]{"相册中选择","相机拍照"};
//                AlertDialog.Builder builder=new AlertDialog.Builder(Maintabs_DActivity.this);
//                builder.setIcon(R.mipmap.isafety);
//                builder.setTitle("选择更换头像方式");
//                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(Maintabs_DActivity.this, "从"+items[which], Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setPositiveButton("yes",null);
//                builder.create().show();

            }
        });

        /*更多精彩按钮*/
        mMore=findViewById(R.id.maintabs_d_body_header_4);
        mMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Maintabs_DActivity.this, MoreActivity.class);
                startActivity(intent);
                //Toast.makeText(Maintabs_DActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });

        mRecordBook=findViewById(R.id.maintabs_d_body_body2_1);
        mRecordBook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Maintabs_DActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });


        //所有页面跳转
        mHelp=findViewById(R.id.maintabs_d_body_footer_2);
        mSetting=findViewById(R.id.maintabs_d_body_header_3);
        mLight=findViewById(R.id.maintabs_d_body_body_1);
        mHistory=findViewById(R.id.maintabs_d_body_header_1);
        mIdsafe=findViewById(R.id.maintabs_d_body_header_2);
        mSafeBook=findViewById(R.id.miantabs_d_body_body_isafe);
        mMiniBook=findViewById(R.id.maintabs_d_footer1);

        mTicket=findViewById(R.id.maintabs_d_body_body2_2);
        mRecommend=findViewById(R.id.maintabs_d_body_body2_3);
        mLinks=findViewById(R.id.maintabs_d_body_body2_4);
        mEidtUser=findViewById(R.id.maintabs_d_edituser);
        mWell=findViewById(R.id.maintabs_d_header_well);


        setListeners();

        /*小手册功能*/
        LinearLayout mAlbum=findViewById(R.id.maintabs_d_footer2);
        mAlbum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Maintabs_DActivity.this, NoticeActivity.class);
                startActivity(intent);
                //Toast.makeText(Maintabs_DActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });

        mWell.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showWellDialog();
            }
        });

    }


    private Emitter.Listener getEmail = new Emitter.Listener() {


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

                        email = data.getString("email");
                        userName=data.getString("username");
                        //Toast.makeText(LoginActivity.this,status+msg, Toast.LENGTH_SHORT).show();
                        setUseremail(email);
                        setUsername(userName);

                        mUseremail.setText(email);
                        mUsername.setText(userName);
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };


    private void setUseremail(String email) {
        //更新数据库字段
        DBUtils.getInstance(this).updateUserInfo("email", email, id);
    }

    private void setUsername(String name) {
        //更新数据库字段
        DBUtils.getInstance(this).updateUserInfo("userName", name, id);
    }


    /**页面跳转*/
    //页面跳转函数
    private void setListeners(){
        Onclick onclick=new Onclick();

        mHelp.setOnClickListener(onclick);
        mSetting.setOnClickListener(onclick);
        mLight.setOnClickListener(onclick);
        mHistory.setOnClickListener(onclick);
        mIdsafe.setOnClickListener(onclick);
        mSafeBook.setOnClickListener(onclick);
        mMiniBook.setOnClickListener(onclick);
        mTicket.setOnClickListener(onclick);
        mRecommend.setOnClickListener(onclick);
        mLinks.setOnClickListener(onclick);
        mEidtUser.setOnClickListener(onclick);

    }

    //点击接口类
    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent=null;
            switch (v.getId()){
                case R.id.maintabs_d_body_footer_2:
                    Toasty.success(Maintabs_DActivity.this, "获取帮助!", Toast.LENGTH_SHORT, true).show();
                    intent=new Intent(Maintabs_DActivity.this,HelpActivity.class);

                    break;
                case R.id.maintabs_d_body_header_3:
                    intent=new Intent(Maintabs_DActivity.this,SettingActivity.class);
                    break;
                case R.id.maintabs_d_body_body_1:
                    intent =new Intent(Maintabs_DActivity.this,com.maintabs_d_secondpages.WolfLightActivity.class);
                    break;
                case R.id.maintabs_d_body_header_1:
                    intent =new Intent(Maintabs_DActivity.this,HistoryActivity.class);
                    break;
                case R.id.maintabs_d_body_header_2:
                    intent =new Intent(Maintabs_DActivity.this,IdsafeActivity.class);
                    break;
                case R.id.miantabs_d_body_body_isafe:
                    intent =new Intent(Maintabs_DActivity.this,Main_C_ListItem2.class);
                    break;
                case R.id.maintabs_d_footer1:
                    intent =new Intent(Maintabs_DActivity.this,com.memorandum.MainActivity.class);
                    break;
                case R.id.maintabs_d_body_body2_3:
                    intent =new Intent(Maintabs_DActivity.this,com.maintabs_d_secondpages.Go_RecommandActivity.class);
                    break;
                case R.id.maintabs_d_body_body2_2:
                    intent =new Intent(Maintabs_DActivity.this,com.maintabs_d_secondpages.Go_TicketActivity.class);
                    break;

                case R.id.maintabs_d_body_body2_4:
                    intent =new Intent(Maintabs_DActivity.this,com.maintabs_d_secondpages.Go_LinksActivity.class);
                    break;
                case R.id.maintabs_d_edituser:
                    intent =new Intent(Maintabs_DActivity.this,com.maintabs_d_secondpages.UserActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

    /**share与feedback相关函数*/
    //分享按钮点击事件
    private void shareDialog() {
        /*ShareBottomDialog dialog = new ShareBottomDialog();
        dialog.show(getSupportFragmentManager());*/
        EditTextDialog dialog = new EditTextDialog();
        dialog.show(getSupportFragmentManager());
    }

    private void showDialog() {
        BottomDialog.create(getSupportFragmentManager())
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        initView(v);
                    }
                })
                .setLayoutRes(R.layout.maintabs_d_share_bottomdialog)
                .setDimAmount(0.4f)
                .setTag("BottomDialog")
                .show();
    }


    /*safergo魅力值*/
    private void showWellDialog() {

        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_maintabs_d_wellabout,null);
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

    private void initView(final View view) {
        /*final EditText editText = (EditText) view.findViewById(R.id.edit_text);
        editText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        editText.setText("Hello world");*/
    }


}





