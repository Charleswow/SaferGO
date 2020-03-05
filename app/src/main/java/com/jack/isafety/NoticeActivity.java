package com.jack.isafety;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.maintabs_d_secondpages.HelpActivity;

public class NoticeActivity extends AppCompatActivity {

    private LinearLayout Notice_1,Notice_2,Notice_3,Notice_4,Notice_5,Notice_6,Notice_7,Notice_8,Notice_9,Notice_10,Notice_11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initView();

        Notice_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog_1();
            }
        });

        Notice_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NoticeActivity.this, "正在策划", Toast.LENGTH_SHORT).show();

                showDialog_2();
            }
        });

        Notice_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NoticeActivity.this, "正在策划", Toast.LENGTH_SHORT).show();
                showDialog_3();
            }
        });
        Notice_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NoticeActivity.this, "正在策划", Toast.LENGTH_SHORT).show();

                showDialog_4();
            }
        });
        Notice_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NoticeActivity.this, "正在策划", Toast.LENGTH_SHORT).show();
                showDialog_5();
            }
        });
        Notice_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NoticeActivity.this, "正在策划", Toast.LENGTH_SHORT).show();

                showDialog_6();
            }
        });
        Notice_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NoticeActivity.this,HelpActivity.class  );
                startActivity(intent);
            }
        });
        Notice_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoticeActivity.this, "正在策划", Toast.LENGTH_SHORT).show();

            }
        });
        Notice_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoticeActivity.this, "正在策划", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initView(){

        Notice_1=findViewById(R.id.notice_1);
        Notice_2=findViewById(R.id.notice_2);
        Notice_3=findViewById(R.id.notice_3);
        Notice_4=findViewById(R.id.notice_4);
        Notice_5=findViewById(R.id.notice_5);
        Notice_6=findViewById(R.id.notice_6);
        Notice_7=findViewById(R.id.notice_7);
        Notice_8=findViewById(R.id.notice_8);
        Notice_9=findViewById(R.id.notice_9);
        Notice_10=findViewById(R.id.notice_10);
        Notice_11=findViewById(R.id.notice_11);
    }

    private void showDialog_1() {

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

    private void showDialog_2() {

        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_maintabs_d_notification2,null);
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

    private void showDialog_3() {

        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_maintabs_d_notification3,null);
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

    private void showDialog_4() {

        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_maintabs_d_notification4,null);
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

    private void showDialog_5() {

        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_maintabs_d_notification5,null);
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

    private void showDialog_6() {

        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_maintabs_d_notification6,null);
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
}
