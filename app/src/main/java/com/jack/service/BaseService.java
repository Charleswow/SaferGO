package com.jack.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class BaseService extends Service {
    //Service启动时调用
    public static Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://39.105.40.62:4000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v("wang", "OnCreate 服务启动时调用");

        mSocket.connect();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    //服务被关闭时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("wang", "onDestroy 服务关闭时");
    }


}
