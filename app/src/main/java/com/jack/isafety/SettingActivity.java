package com.jack.isafety;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.maintabs_d_secondpages.UserActivity;


public class SettingActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if(NavUtils.getParentActivityName(SettingActivity.this)!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //设置点击监听
        findViewById(R.id.setting_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.setting_well).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWellDialog();
                //Toast.makeText(SettingActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.setting_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.setting_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.setting_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getAppDetailSettingIntent());
                //Toast.makeText(SettingActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.setting_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.setting_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SettingActivity.this, "团队介绍", Toast.LENGTH_SHORT).show();
                showGrounpDialog();
            }
        });

    }



    /*权限界面*/

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }


    /*safergo团队简介页面*/
    private void showGrounpDialog() {

        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_maintabs_d_groupabout,null);
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

}
