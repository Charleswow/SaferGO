package com.jack.isafety;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main_C_ListItem1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__c__list_item1);

        if(NavUtils.getParentActivityName(Main_C_ListItem1.this)!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
