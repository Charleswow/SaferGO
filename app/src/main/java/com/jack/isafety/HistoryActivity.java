package com.jack.isafety;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        findViewById(R.id.history_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(HistoryActivity.this, "清除成功!", Toast.LENGTH_SHORT, true).show();

            }
        });


        //listview_item的添加
        //素材数组的构建
        int[] Listview_item_logo=new int[]{
                R.mipmap.safergo
        };

        String[] Listview_item_title=new String[]{
                "时时守护"
        };
        String[] Listview_item_time=new String[]{
                "2019-03-01"
        };
        String[] Listview_item_id=new String[]{
                "SaferGo"
        };

        List<Map<String,Object>> List_item=new ArrayList<Map<String,Object>>();
        for(int i=0;i<Listview_item_logo.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("main_listview_item_logo",Listview_item_logo[i]);
            map.put("main_listview_item_title",Listview_item_title[i]);
            map.put("main_listview_item_time",Listview_item_time[i]);
            map.put("main_listview_item_id",Listview_item_id[i]);
            List_item.add(map);
        }

        //配置适配器
        SimpleAdapter adapter=new SimpleAdapter(this,List_item, R.layout.maintabs_a_listview_item,new String[]{"main_listview_item_logo","main_listview_item_title","main_listview_item_time","main_listview_item_id"},
                new int[]{R.id.main_listview_item_logo, R.id.main_listview_item_title,R.id.main_listview_item_time,R.id.main_listview_item_id});
        ListView mMain_listview=findViewById(R.id.history_record);
        mMain_listview.setAdapter(adapter);

        //监听点击
        mMain_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) parent.getItemAtPosition(position);

                Toasty.success(HistoryActivity.this, map.get("main_listview_item_id").toString()+"在"+map.get("main_listview_item_time").toString()+"守护了您", Toast.LENGTH_SHORT, true).show();

            }
        });

    }
}
