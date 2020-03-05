package com.jack.sqlite;

import org.json.JSONArray;

import java.lang.reflect.Array;

import xin.skingorz.internet.Friend;

public class UserBean {

    static public String id="SaferGo";
    static public String email;
    static public String userName;//用户名
    static public String phone;//电话
    static public String sex;//性别
    static public String signature;//签名
    static public String home;//籍贯
    static public String location;//地址

    public static String phoneNumber;//预设手机号吧
    public static String messageText;//信息模板
    public static String currentLocation;//当前地址

    public static JSONArray Friend = new JSONArray(); //做多添加１００个好友


}
