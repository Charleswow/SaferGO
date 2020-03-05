package com.jack.isafety;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*import xin.skingorz.isafety.Login;
import xin.skingorz.isafety.modify_pwd;
import xin.skingorz.isafety.returnMsg;
import xin.skingorz.isafety.sendFindPwdEmail;*/

public class Forget2Activity extends AppCompatActivity {


    private EditText mEmail,mF_Password,mF_PasswordAgain;
    private TextView mOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget2);


        //获取
        mEmail=findViewById(R.id.forget_email);
        mF_Password=findViewById(R.id.forget_password);
        mF_PasswordAgain=findViewById(R.id.forget_password_again);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String rEmail=bundle.getString("email");


        //修改密码
        mOK=findViewById(R.id.forget_btn_password_change);
        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Forget2Activity.this, "已点击", Toast.LENGTH_SHORT).show();
                //声明
/*                Callable<returnMsg> callable = null;
                try {
                    callable = new modify_pwd(rEmail,mF_Password.getText().toString(),mF_PasswordAgain.getText().toString());
                    Log.i("email,password",rEmail+""+mF_Password.getText().toString()+""+mF_PasswordAgain.getText().toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                FutureTask<returnMsg> futureTask = new FutureTask<returnMsg>(callable);
                new Thread(futureTask).start();
                while (!futureTask.isDone()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    returnMsg returnMsg = futureTask.get();
                    Log.i("returnMsg", returnMsg.getMsg());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (returnMsg.getStatus() == 200) {
                    //测试
                    Intent intent=new Intent(Forget2Activity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(Forget2Activity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Log.i("successprint","fff");
                } else {
                    Toast.makeText(Forget2Activity.this, returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                    Log.i("failprint","fff");
                }*/

            }
        });

    }
}
