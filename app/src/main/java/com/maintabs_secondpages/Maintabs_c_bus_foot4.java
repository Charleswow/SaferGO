package com.maintabs_secondpages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jack.isafety.R;

public class Maintabs_c_bus_foot4 extends AppCompatActivity {

    private WebView bus_foot4;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs_c_bus_foot4);

        bus_foot4=findViewById(R.id.bus_foot4);

        bus_foot4.loadUrl("https://m.huoche.net/gongjiao/");

        WebSetting(bus_foot4);
    }

    public void WebSetting(WebView webView){


        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

        //支持app内js交互
        webView.getSettings().setJavaScriptEnabled(true);

        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        //设置了支持缩放
        webView.getSettings().setSupportZoom(true);

        //扩大比例缩放
        webView.getSettings().setUseWideViewPort(true);

        //设置是否出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);

    }
}
