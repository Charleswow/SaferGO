package com.maintabs_secondpages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.isafety.R;


public class Maintabs_c_plane extends AppCompatActivity {

    private LinearLayout Header_p_1;
    private LinearLayout Header_p_2;
    private LinearLayout Header_p_3;
    private LinearLayout Header_p_4;

    private TextView Header_Show,Body_Show;

    Uri uri1 = Uri.parse("https://m.airchina.com.cn/ac/");
    Uri uri2 = Uri.parse("https://m.csair.com/touch/com.csair.mbp.index/index.html?WT.mc_id=sem-baidu-mbzc-title0201&WT.srch=1");
    Uri uri3 = Uri.parse("https://m.ceair.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs_c_plane);

        initView();

        setListeners();

    }

    //初始化
    private void initView(){

        Header_p_1=findViewById(R.id.maintabs_c_plane_header_1);
        Header_p_2=findViewById(R.id.maintabs_c_plane_header_2);
        Header_p_3=findViewById(R.id.maintabs_c_plane_header_3);
        Header_p_4=findViewById(R.id.maintabs_c_plane_header_4);
        Header_Show=findViewById(R.id.miantabs_plane_body_body_show);
        Body_Show=findViewById(R.id.miantabs_plane_body_body_show2);

    }


    //点击跳转
    private void setListeners(){
        Maintabs_c_plane.Onclick onclick=new Maintabs_c_plane.Onclick();

        Header_p_4.setOnClickListener(onclick);
        Header_p_3.setOnClickListener(onclick);
        Header_p_2.setOnClickListener(onclick);
        Header_p_1.setOnClickListener(onclick);
        Header_Show.setOnClickListener(onclick);
        Body_Show.setOnClickListener(onclick);

    }


    //点击接口类
    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent=null;
            switch (v.getId()){
                case R.id.maintabs_c_plane_header_1:
                    intent = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(intent);
                    break;
                case R.id.maintabs_c_plane_header_2:
                    intent = new Intent(Intent.ACTION_VIEW, uri2);
                    startActivity(intent);
                    break;
                case R.id.maintabs_c_plane_header_3:
                    intent = new Intent(Intent.ACTION_VIEW, uri3);
                    startActivity(intent);
                    break;
                case R.id.maintabs_c_plane_header_4:
                    Toast.makeText(com.maintabs_secondpages.Maintabs_c_plane.this, "待开发", Toast.LENGTH_SHORT).show();
                    //intent=new Intent(com.maintabs_secondpages.Maintabs_c_plane.this,com.maintabs_secondpages.Maintabs_c_subway_notice.class);
                    break;
                case R.id.miantabs_plane_body_body_show:
                    Toast.makeText(com.maintabs_secondpages.Maintabs_c_plane.this, "说明", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.miantabs_plane_body_body_show2:
                    Toast.makeText(com.maintabs_secondpages.Maintabs_c_plane.this, "计划", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }
}
