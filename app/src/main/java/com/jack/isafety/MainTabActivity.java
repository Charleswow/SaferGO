package com.jack.isafety;



import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.maintabs_d_secondpages.HelpActivity;


import static com.jack.sqlite.UserBean.phoneNumber;



public class MainTabActivity extends TabActivity implements OnCheckedChangeListener{




    /**
     * 传感器
     */
    private SensorManager sensorManager;
    private MainTabActivity.ShakeSensorListener shakeListener;

    /**
     * 判断一次摇一摇动作
     */
    private boolean isShake = false;



    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    public static MainTabActivity instance;


    private TabHost mTabHost;
    private Intent mAIntent;
    private Intent mBIntent;
    private Intent mCIntent;
    private Intent mDIntent;


    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION};


    //FloatingSearchView mSearchView=findViewById(R.id.floating_search_view);


    //actionbar添加
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void resetSprfMain(){
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();
        editorMain.putBoolean("main",false);
        editorMain.commit();
    }


    //监听菜单点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.actionbar:
                Intent intent1=new Intent(MainTabActivity.this,Maintabs_BActivity.class);
                startActivity(intent1);
                break;

            case R.id.homepage:
                resetSprfMain();
                Intent intent=new Intent(MainTabActivity.this,LoginActivity.class);

                MainTabActivity.this.finish();
                startActivity(intent);
                break;

            case R.id.help:
                Intent intent2=new Intent(MainTabActivity.this,com.memorandum.MainActivity.class);
                startActivity(intent2);
                break;

            case R.id.feedback:
                Intent intent3=new Intent(MainTabActivity.this,NoticeActivity.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.maintabs);


/*        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SlidingLayout.isLeftLayoutVisible()) {
                    SlidingLayout.scrollToRightLayout();
                } else {
                    SlidingLayout.scrollToLeftLayout();
                }
            }
        });*/


        /*摇一摇相关*/
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        shakeListener = new MainTabActivity.ShakeSensorListener();



        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]);
            int l = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[1]);
            int m = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[2]);
            int n = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[3]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED || l != PackageManager.PERMISSION_GRANTED || m != PackageManager.PERMISSION_GRANTED ||
                    n != PackageManager.PERMISSION_GRANTED) {   startRequestPermission();}}

        // 如果没有授予该权限，就去提示用户请求

        //在oncreate中添加
        instance = this;

        this.mAIntent = new Intent(this,Maintabs_AActivity.class);
        this.mBIntent = new Intent(this,Maintabs_BActivity.class);
        this.mCIntent = new Intent(this,Maintabs_CActivity.class);
        this.mDIntent = new Intent(this,Maintabs_DActivity.class);


        ((RadioButton) findViewById(R.id.radio_button0))
                .setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button1))
                .setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button2))
                .setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button3))
                .setOnCheckedChangeListener(this);

        setupIntent();

    }


    /**
     * 开始提交请求权限
     */
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    /**
     * 用户权限 申请 的回调方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //如果没有获取权限，那么可以提示用户去设置界面--->应用权限开启权限
                } else {
                    //获取权限成功提示，可以不要
                    Toast toast = Toast.makeText(this, "获取权限成功", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            switch (buttonView.getId()) {
                case R.id.radio_button0:
                    this.mTabHost.setCurrentTabByTag("A_TAB");
                    break;
                case R.id.radio_button1:
                    this.mTabHost.setCurrentTabByTag("B_TAB");
                    break;
                case R.id.radio_button2:
                    this.mTabHost.setCurrentTabByTag("C_TAB");
                    break;
                case R.id.radio_button3:
                    this.mTabHost.setCurrentTabByTag("D_TAB");
                    break;
            }
        }

    }

    private void setupIntent() {
        this.mTabHost = getTabHost();
        TabHost localTabHost = this.mTabHost;

        localTabHost.addTab(buildTabSpec("A_TAB", R.string.main_home,
                R.drawable.icon_1_n, this.mAIntent));

        localTabHost.addTab(buildTabSpec("B_TAB", R.string.main_news,
                R.drawable.icon_2_n, this.mBIntent));

        localTabHost.addTab(buildTabSpec("C_TAB", R.string.main_manage_date,
                R.drawable.icon_3_n, this.mCIntent));

        localTabHost.addTab(buildTabSpec("D_TAB", R.string.main_friends,
                R.drawable.icon_4_n, this.mDIntent));

    }

    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
                                         final Intent content) {
        return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),
                getResources().getDrawable(resIcon)).setContent(content);

    }

    /**设置震动*/
    @Override
    protected void onResume() {
        //注册监听加速度传感器
        sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    protected void onPause() {
        /**
         * 资源释放
         */
        sensorManager.unregisterListener(shakeListener);
        super.onPause();
    }

    private class ShakeSensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            //避免一直摇
            if (isShake) {
                return;
            }
            float[] values = event.values;
            /*
             * x : x轴方向的重力加速度，向右为正
             * y : y轴方向的重力加速度，向前为正
             * z : z轴方向的重力加速度，向上为正
             */
            float x = Math.abs(values[0]);
            float y = Math.abs(values[1]);
            float z = Math.abs(values[2]);
            //加速度超过72，摇一摇成功
            if (x > 72 || y > 72 || z > 72) {
                isShake = true;
                //播放声音
                //playSound(MainTabActivity.this);
                //震动，注意权限
                vibrate( 500);
                //仿网络延迟操作，这里可以去请求服务器...
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //弹框
                        showDialog();
                    }
                },1000);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    //摇动音效
    /*private void playSound(Context context) {
        MediaPlayer player = MediaPlayer.create(context,R.raw.shake_sound);
        player.start();
    }*/

    private void vibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    private void showDialog() {

        call(phoneNumber);

        isShake = false;
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


}
