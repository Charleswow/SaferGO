package com.maintabs_secondpages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jack.isafety.R;

public class Maintabs_c_bus extends AppCompatActivity {

    private LinearLayout Header_b_search;
    private LinearLayout Header_b_card;
    private LinearLayout Header_b_trip;
    private LinearLayout Header_b_net;

    private LinearLayout Footer_b_1;
    private LinearLayout Footer_b_2;
    private LinearLayout Footer_b_3;
    private LinearLayout Footer_b_4;
    private LinearLayout Footer_b_5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs_c_bus);

        initView();

        setListeners();
    }

    //初始化
    private void initView(){

        Header_b_search=findViewById(R.id.maintabs_c_bus_header_1);
        Header_b_card=findViewById(R.id.maintabs_c_bus_header_2);
        Header_b_trip=findViewById(R.id.maintabs_c_bus_header_3);
        Header_b_net=findViewById(R.id.maintabs_c_bus_header_4);
        Footer_b_1=findViewById(R.id.maintabs_c_bus_footer_1);
        Footer_b_2=findViewById(R.id.maintabs_c_bus_footer_2);
        Footer_b_3=findViewById(R.id.maintabs_c_bus_footer_3);
        Footer_b_4=findViewById(R.id.maintabs_c_bus_footer_4);
        Footer_b_5=findViewById(R.id.maintabs_c_bus_footer_5);

    }


    //点击跳转
    private void setListeners(){
        Maintabs_c_bus.Onclick onclick=new Maintabs_c_bus.Onclick();

        Header_b_search.setOnClickListener(onclick);
        Header_b_card.setOnClickListener(onclick);
        Header_b_trip.setOnClickListener(onclick);
        Header_b_net.setOnClickListener(onclick);
        Footer_b_1.setOnClickListener(onclick);
        Footer_b_2.setOnClickListener(onclick);
        Footer_b_3.setOnClickListener(onclick);
        Footer_b_4.setOnClickListener(onclick);
        Footer_b_5.setOnClickListener(onclick);

    }


    //点击接口类
    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent=null;
            switch (v.getId()){
                case R.id.maintabs_c_bus_header_1:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_bus.this,com.maintabs_secondpages.Maintabs_c_bus_search.class);
                    break;
                case R.id.maintabs_c_bus_header_2:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_bus.this,com.maintabs_secondpages.Maintabs_c_bus_card.class);
                    break;
                case R.id.maintabs_c_bus_header_3:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_bus.this,com.maintabs_secondpages.Maintabs_c_bus_trip.class);
                    break;
                case R.id.maintabs_c_bus_header_4:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_bus.this,com.maintabs_secondpages.Maintabs_c_bus_net.class);
                    break;
                case R.id.maintabs_c_bus_footer_1:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_bus.this,com.maintabs_secondpages.Maintabs_c_bus_foot1.class);
                    break;
                case R.id.maintabs_c_bus_footer_2:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_bus.this,com.maintabs_secondpages.Maintabs_c_bus_foot2.class);
                    break;
                case R.id.maintabs_c_bus_footer_3:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_bus.this,com.maintabs_secondpages.Maintabs_c_bus_foot3.class);
                    break;
                case R.id.maintabs_c_bus_footer_4:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_bus.this,com.maintabs_secondpages.Maintabs_c_bus_foot4.class);
                    break;
                case R.id.maintabs_c_bus_footer_5:
                    intent=new Intent(com.maintabs_secondpages.Maintabs_c_bus.this,com.maintabs_secondpages.Maintabs_c_bus_foot5.class);
                    break;

            }
            startActivity(intent);
        }
    }

}
