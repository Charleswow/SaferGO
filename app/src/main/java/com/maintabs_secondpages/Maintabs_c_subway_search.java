package com.maintabs_secondpages;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jack.isafety.R;

public class Maintabs_c_subway_search extends Activity {

    private WebView subway_search;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maintabs_c_subway_search);

        initView();

        subway_search.loadUrl("https://map.bjsubway.com/mobile");

        subway_search.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

        //支持app内js交互
        subway_search.getSettings().setJavaScriptEnabled(true);

        //自适应屏幕
        subway_search.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        subway_search.getSettings().setLoadWithOverviewMode(true);

        //设置了支持缩放
        subway_search.getSettings().setSupportZoom(true);

        //扩大比例缩放
        subway_search.getSettings().setUseWideViewPort(true);

        //设置是否出现缩放工具
        subway_search.getSettings().setBuiltInZoomControls(true);
    }

    //初始化
    private void initView(){

        subway_search=findViewById(R.id.subway_search);

    }

}
