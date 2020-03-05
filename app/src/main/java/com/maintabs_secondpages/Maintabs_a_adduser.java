package com.maintabs_secondpages;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.isafety.R;
import com.jack.sqlite.UserBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.socket.emitter.Emitter;
import xin.skingorz.internet.Friend;
import xin.skingorz.internet.Search;

import static com.jack.service.BaseService.mSocket;
import static com.jack.sqlite.UserBean.home;
import static com.jack.sqlite.UserBean.location;
import static com.jack.sqlite.UserBean.phone;
import static com.jack.sqlite.UserBean.sex;
import static com.jack.sqlite.UserBean.signature;
import static com.jack.sqlite.UserBean.userName;


public class Maintabs_a_adduser extends AppCompatActivity {

    private EditText mSearchView;

    private Friend friend=new Friend();

    private Search search=new Search();

    private TextView mSearchName;

    private  Button mAdduserBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs_a_adduser);


        mSearchView= findViewById(R.id.searchView);
        mSearchName=findViewById(R.id.adduser_name);

        mAdduserBtn=findViewById(R.id.adduser_btn);

        mAdduserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,String> map=new HashMap<String, String>();
                map.put("search",mSearchView.getText().toString());

                net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(map);

                mSocket.emit("getInfor",jsonObject);



            }
        });

        mSocket.on("searchResult",getInformation);


        mSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,String> map=new HashMap<String, String>();
                map.put("friend",mSearchView.getText().toString());

                net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(map);

                mSocket.emit("AddFriend",jsonObject);

                //mSocket.on("addFriendResult",Listener);

                onBackPressed();

            }
        });

    }


    private Emitter.Listener getInformation = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            //主线程调用
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = null;
                    try {
                        data = new JSONObject((String)args[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String[] Listview_item_email = new String[20];


                    try {
                        UserBean.Friend= data.getJSONArray("friend");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mSearchName.setText(mSearchView.getText().toString());

                    for(int i=0;i< UserBean.Friend.length()-1;i++){

                        try {
                            Listview_item_email[i]= ((JSONObject) UserBean.Friend.get(i)).getString("friends_email");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(Listview_item_email[i]==""){
                            //Friend[i] = data.getString("username");



                            break;
                        }
                    }

                }
            });
        }
    };
}
