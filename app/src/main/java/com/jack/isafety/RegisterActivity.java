package com.jack.isafety;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.specialEffects.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import xin.skingorz.internet.Register;


public class RegisterActivity extends Activity {

    private EditText mEmail, mNumber, mUsername, mPassword, mPassagain;


    Register register=new Register();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        //获取
        mEmail = findViewById(R.id.register_register_email);
        mNumber = findViewById(R.id.register_register_number);
        mUsername = findViewById(R.id.register_register_username);
        mPassword = findViewById(R.id.register_register_password);
        mPassagain = findViewById(R.id.register_register_password_again);


        //返回登录页面
        ImageView mRegister_Back = findViewById(R.id.mregister_back);
        mRegister_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);*/
                onBackPressed();
            }
        });

        TextView mRegister_Login = findViewById(R.id.mregister_login);
        mRegister_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        //注册按钮
        Button mRegister_prove = findViewById(R.id.register_email_prove);
        mRegister_prove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(RegisterActivity.this, "已点击", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject=register.get_code(mEmail.getText().toString());

                    Toast.makeText(RegisterActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        //监听注册
        TextView mRegister_Register = findViewById(R.id.main_btn_register);
        mRegister_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if(StringUtils.checkPhoneNumber(mUsername.getText().toString())||StringUtils.checkEmail(mUsername.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "用户名不能为邮箱或手机号，请重新命名", Toast.LENGTH_SHORT).show();

                    }else{
                        //注册
                        JSONObject jsonobject = register.add_user(mEmail.getText().toString(),mNumber.getText().toString(),mUsername.getText().toString(),mPassword.getText().toString(),mPassagain.getText().toString());

                        Toast.makeText(RegisterActivity.this, jsonobject.getString("msg"), Toast.LENGTH_SHORT).show();

                        onBackPressed();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        });

    }
}
