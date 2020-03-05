package com.maintabs_secondpages;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.isafety.R;

import es.dmoral.toasty.Toasty;


public class Maintabs_c_subway extends AppCompatActivity {


    private LinearLayout Header_others;
    private LinearLayout Header_search;
    private LinearLayout Header_stop;
    private LinearLayout Header_notice;
    private TextView Header_china,Header_Show;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs_c_subway);

        initView();

        setListeners();



        Header_china.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线

        Header_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(Maintabs_c_subway.this, "更多精彩", Toast.LENGTH_SHORT, true).show();

            }
        });


    }


    //初始化
    private void initView(){

        Header_others=findViewById(R.id.maintabs_c_subway_header_4);
        Header_search=findViewById(R.id.maintabs_c_subway_header_1);
        Header_stop=findViewById(R.id.maintabs_c_subway_header_2);
        Header_notice=findViewById(R.id.maintabs_c_subway_header_3);
        Header_china=findViewById(R.id.miantabs_subway_body_body_show2);
        Header_Show=findViewById(R.id.miantabs_subway_body_body_show);


    }


    //点击跳转
    private void setListeners(){
        Maintabs_c_subway.Onclick onclick=new Maintabs_c_subway.Onclick();

        Header_others.setOnClickListener(onclick);
        Header_search.setOnClickListener(onclick);
        Header_stop.setOnClickListener(onclick);
        Header_notice.setOnClickListener(onclick);
        Header_china.setOnClickListener(onclick);



    }


    //点击接口类
    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent=null;
            switch (v.getId()){
                case R.id.maintabs_c_subway_header_4:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_subway.this,com.maintabs_secondpages.Maintabs_c_subway_others.class);
                    break;
                case R.id.maintabs_c_subway_header_1:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_subway.this,com.maintabs_secondpages.Maintabs_c_subway_search.class);
                    break;
                case R.id.maintabs_c_subway_header_2:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_subway.this,com.maintabs_secondpages.Maintabs_c_subway_stop.class);
                    break;
                case R.id.maintabs_c_subway_header_3:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_subway.this,com.maintabs_secondpages.Maintabs_c_subway_notice.class);
                    break;
                case R.id.miantabs_subway_body_body_show2:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_subway.this,com.maintabs_secondpages.Maintabs_c_subway_china.class);
                    break;


            }
            startActivity(intent);
        }
    }

}
