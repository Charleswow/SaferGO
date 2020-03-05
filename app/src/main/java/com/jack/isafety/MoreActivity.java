package com.jack.isafety;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.maintabs_d_secondpages.More_tools;

public class MoreActivity extends AppCompatActivity {

    private LinearLayout mTools;

    /**
     * 传感器
     */
    private SensorManager sensorManager;
    private MoreActivity.ShakeSensorListener shakeListener;

    /**
     * 判断一次摇一摇动作
     */
    private boolean isShake = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        initView();

        setListeners();

        /*摇一摇相关*/
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        shakeListener = new MoreActivity.ShakeSensorListener();
    }

    private void initView(){
        mTools=findViewById(R.id.more_tools);
    }

    //点击跳转
    private void setListeners(){
        MoreActivity.Onclick onclick=new MoreActivity.Onclick();

        mTools.setOnClickListener(onclick);


    }


    //点击接口类
    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent=null;
            switch (v.getId()){
                case R.id.more_tools:
                    intent=new Intent(MoreActivity.this,More_tools.class);
                    break;
            }
            startActivity(intent);
        }
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
                playSound(MoreActivity.this);
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

    private void playSound(Context context) {
        MediaPlayer player = MediaPlayer.create(context,R.raw.shake_sound);
        player.start();
    }

    private void vibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    private void showDialog() {

        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_maintabs_shake,null);
        mAlertDialog.setContentView(view);

        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //这里让弹框取消后，才可以执行下一次的摇一摇
                isShake = false;
                mAlertDialog.cancel();
            }
        });
        Window window = mAlertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

}
