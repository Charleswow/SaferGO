package com.jack.isafety;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import xin.skingorz.Bean.User;
import xin.skingorz.internet.Search;
import xin.skingorz.utils.GlobalVariable;

import static com.jack.sqlite.UserBean.email;
import static com.jack.sqlite.UserBean.userName;

public class IdsafeActivity extends AppCompatActivity {


    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    private LinearLayout mExit;

    private TextView mForget;

    private TextView mUsername,mUseremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idsafe);

        //退出登录
        mExit=findViewById(R.id.idsafe_exit);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetSprfMain();
                Intent intent=new Intent(IdsafeActivity.this,LoginActivity.class);
                startActivity(intent);
                IdsafeActivity.this.finish();
                MainTabActivity.instance.finish();
            }
        });

        //忘记密码
        mForget=findViewById(R.id.idsafe_forget);
        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IdsafeActivity.this,Forget1Activity.class);
                startActivity(intent);
            }
        });


        mUsername=findViewById(R.id.idsafe_username);
        mUseremail=findViewById(R.id.idsafe_email);

        mUsername.setText(userName);
        mUseremail.setText(email);


        findViewById(R.id.idsafe_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IdsafeActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.idsafe_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(IdsafeActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void resetSprfMain(){
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();
        editorMain.putBoolean("main",false);
        editorMain.commit(); }
}
