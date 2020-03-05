package com.maintabs_secondpages;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jack.isafety.R;

public class Maintabs_c_bus_card extends Activity {

    private WebView bus_card;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maintabs_c_bus_card);

        bus_card=findViewById(R.id.bus_card);

        bus_card.loadUrl("http://www.bjbus.com/api/index.php#/recharge");
        bus_card.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

        //支持app内js交互
        bus_card.getSettings().setJavaScriptEnabled(true);

        //自适应屏幕
        bus_card.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        bus_card.getSettings().setLoadWithOverviewMode(true);

        //设置了支持缩放
        bus_card.getSettings().setSupportZoom(true);

        //扩大比例缩放
        bus_card.getSettings().setUseWideViewPort(true);

        //设置是否出现缩放工具
        bus_card.getSettings().setBuiltInZoomControls(true);
    }
}
