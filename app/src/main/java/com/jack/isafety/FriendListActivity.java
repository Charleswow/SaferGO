package com.jack.isafety;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FriendListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_friend_list);


        int[] Listview_item_logo=new int[]{
                R.mipmap.safergo, /*R.drawable.protect, R.color.main_color_darkblue, R.color.main_color_darkgreen,
*/
        };
        String[] Listview_item_title=new String[]{
                "jack"/*,"SaferGo","test","demo"*/
        };
        String[] Listview_item_email=new String[]{
                "2508074836@qq.com"/*,"email","email","email"*/
        };


        //listview_item的添加
        //素材数组的构建

        List<Map<String,Object>> List_item=new ArrayList<Map<String,Object>>();
        for(int i=0;i<Listview_item_logo.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("main_listview_item_logo",Listview_item_logo[i]);
            map.put("main_listview_item_title",Listview_item_title[i]);
            map.put("main_listview_item_email",Listview_item_email[i]);
            List_item.add(map);
        }

        //配置适配器
        SimpleAdapter adapter=new SimpleAdapter(this,List_item, R.layout.friendlist,new String[]{"main_listview_item_logo","main_listview_item_title","main_listview_item_email"},
                new int[]{R.id.main_listview_item_logo, R.id.main_listview_item_title,R.id.main_listview_item_id});
        ListView mMain_listview=findViewById(R.id.friendlist_listview);
        mMain_listview.setAdapter(adapter);

        //监听点击
        mMain_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) parent.getItemAtPosition(position);
                Toast.makeText(FriendListActivity.this, map.get("main_listview_item_title").toString(), Toast.LENGTH_SHORT).show();

                onBackPressed();

                /*switch (position){
                    case 0:

                        break;
                }*/

            }
        });

    }
}
