package com.maintabs_secondpages;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jack.isafety.R;

public class Maintabs_c_subway_china extends Activity {

    private WebView subway_china;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maintabs_c_subway_china);

        initView();

        subway_china.loadUrl("https://m.8684.cn/dt_switch");

        subway_china.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
        });

        //支持app内js交互
        subway_china.getSettings().setJavaScriptEnabled(true);

        //自适应屏幕
        subway_china.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        subway_china.getSettings().setLoadWithOverviewMode(true);

        //设置了支持缩放
        subway_china.getSettings().setSupportZoom(true);

        //扩大比例缩放
        subway_china.getSettings().setUseWideViewPort(true);

        //设置是否出现缩放工具
        subway_china.getSettings().setBuiltInZoomControls(true);
        
        
    }

    //初始化
    private void initView(){

        subway_china=findViewById(R.id.subway_china);

    }
}
