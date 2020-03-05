package com.jack.isafety;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*import xin.skingorz.isafety.Login;
import xin.skingorz.isafety.checkFindPwdCode;
import xin.skingorz.isafety.returnMsg;
import xin.skingorz.isafety.sendFindPwdEmail;*/

public class Forget1Activity extends AppCompatActivity {

    private EditText mEmail,mNumber;
    private Button mEmail_Prove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget1);


        //发送验证码
        mEmail_Prove=findViewById(R.id.forget_email_prove);
        mEmail_Prove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(Forget1Activity.this, "已点击", Toast.LENGTH_SHORT).show();
                //声明
/*                Callable<returnMsg> callable = new sendFindPwdEmail(mEmail.getText().toString());
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
                }*/
            }
        });


        //验证按钮
        TextView mProve=findViewById(R.id.forget_btn_prove);
        mProve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //声明
/*                Callable<returnMsg> callable = new checkFindPwdCode(mEmail.getText().toString(),mNumber.getText().toString());
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


                    //数据传输
                    String bEmail=(findViewById(R.id.forget_email)).getContext().toString();
                    if(!"".equals(bEmail))
                    {
                        Intent intent=new Intent(Forget1Activity.this,Forget2Activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putCharSequence("email",bEmail);

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }else{
                        Toast.makeText(Forget1Activity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                    }
                    //测试
                    Toast.makeText(Forget1Activity.this, "验证成功", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(Forget1Activity.this, returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                }*/



            }
        });


        //获取
        mEmail=findViewById(R.id.forget_email);
        mNumber=findViewById(R.id.forget_number);


    }
}
