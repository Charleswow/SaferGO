package com.maintabs_d_secondpages;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.jack.isafety.R;

public class viusal_voiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viusal_voice);
        final MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.v1);

        Button btn_play1=(Button) findViewById(R.id.voice1);


        btn_play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();

            }});

        final MediaPlayer mediaPlayer2=MediaPlayer.create(this,R.raw.v2);

        Button btn_play2=(Button) findViewById(R.id.voice2);

        btn_play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer2.start();

            }});



        final MediaPlayer mediaPlayer3=MediaPlayer.create(this,R.raw.v3);

        Button btn_play3=(Button) findViewById(R.id.voice3);

        btn_play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer3.start();

            }});


    }

    @Override
    public void onBackPressed() {

        //TODO something
        viusal_voiceActivity.this.finish();

        super.onBackPressed();


    }
}
