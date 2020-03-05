package com.maintabs_secondpages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jack.isafety.R;

public class Maintabs_c_bus_foot2 extends AppCompatActivity {

    private WebView bus_foot2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs_c_bus_foot2);

        bus_foot2=findViewById(R.id.bus_foot2);

        bus_foot2.loadUrl("https://map.baidu.com/mobile/webapp/third/transit/city=%E5%8C%97%E4%BA%AC%E5%B8%82&code=131&force=superman/?third_party=webapp-aladdin&ala_tpl=bus_general&ala_item=title&dgr=3");

        WebSetting(bus_foot2);
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
