package com.maintabs_secondpages;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jack.isafety.R;

public class Maintabs_c_bus_net extends Activity {

    private WebView bus_net;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maintabs_c_bus_net);

        bus_net=findViewById(R.id.bus_net);

        bus_net.loadUrl("http://www.bjbus.com/api/index.php#/");
        bus_net.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

        //支持app内js交互
        bus_net.getSettings().setJavaScriptEnabled(true);

        //自适应屏幕
        bus_net.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        bus_net.getSettings().setLoadWithOverviewMode(true);

        //设置了支持缩放
        bus_net.getSettings().setSupportZoom(true);

        //扩大比例缩放
        bus_net.getSettings().setUseWideViewPort(true);

        //设置是否出现缩放工具
        bus_net.getSettings().setBuiltInZoomControls(true);
    }
}
