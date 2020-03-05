package com.maintabs_d_secondpages;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jack.isafety.R;

public class Go_TicketActivity extends Activity {

    private WebView Ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_go__ticket);

        Ticket=findViewById(R.id.maintabs_d_ticket);

        Ticket.loadUrl("https://www.12306.cn/index/");

        WebSetting(Ticket);
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
