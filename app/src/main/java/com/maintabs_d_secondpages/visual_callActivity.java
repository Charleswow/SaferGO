package com.maintabs_d_secondpages;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.jack.isafety.R;


public class visual_callActivity extends Activity {

    public static visual_callActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_visual_call);

        instance = this;

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.start();


        Button btn_stop = findViewById(R.id.stop);
        Button btn_jietong =  findViewById(R.id.jietong);


        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();
                visual_callActivity.this.finish();

            }
        });

        btn_jietong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();

                Intent intent = new Intent(visual_callActivity.this, jietou.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onBackPressed() {

        Toast.makeText(instance, "点击挂断退出", Toast.LENGTH_SHORT).show();

    }

}
