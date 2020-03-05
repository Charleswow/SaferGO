package com.memorandum;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jack.isafety.R;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        AlertDialog alert=new AlertDialog.Builder(this).create();
        alert.setIcon(R.mipmap.isafety);
        alert.setTitle("传递");
        alert.setMessage("传递zhengnengliang");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
}
