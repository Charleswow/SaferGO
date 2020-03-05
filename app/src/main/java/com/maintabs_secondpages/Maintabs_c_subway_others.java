package com.maintabs_secondpages;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jack.isafety.R;

public class Maintabs_c_subway_others extends Activity {

    private WebView bj_subway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maintabs_c_subway_others);

        initView();

        bj_subway.loadUrl("https://www.bjsubway.com/mobile/");

        bj_subway.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
        });

        //支持app内js交互
        bj_subway.getSettings().setJavaScriptEnabled(true);

        //自适应屏幕
        bj_subway.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        bj_subway.getSettings().setLoadWithOverviewMode(true);

        //设置了支持缩放
        bj_subway.getSettings().setSupportZoom(true);

        //扩大比例缩放
        bj_subway.getSettings().setUseWideViewPort(true);

        //设置是否出现缩放工具
        bj_subway.getSettings().setBuiltInZoomControls(true);


    }

    //初始化
    private void initView(){

        bj_subway=findViewById(R.id.bj_subway);

    }


}
