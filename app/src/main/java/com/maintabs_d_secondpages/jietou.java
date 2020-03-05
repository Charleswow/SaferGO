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

import com.jack.isafety.Maintabs_DActivity;
import com.jack.isafety.R;

public class jietou extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jietou);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.v1);
        mediaPlayer.start();

        Button btn_stop = (Button) findViewById(R.id.stop);


        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(jietou.this, Maintabs_DActivity.class);
                startActivity(intent);*/


                mediaPlayer.stop();

                jietou.this.finish();
                visual_callActivity.instance.finish();
                onBackPressed();


            }
        });




    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "点击挂断退出", Toast.LENGTH_SHORT).show();

    }
}
