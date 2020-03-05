package com.maintabs_secondpages;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.jack.isafety.R;

public class Maintabs_a_searchuser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maintabs_a_searchuser);
    }
}
