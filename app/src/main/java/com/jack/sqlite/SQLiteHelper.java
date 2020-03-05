package com.jack.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static String DB_NAME = "bxg.db";
    public static final String U_USER_INFO = "userInfo";


    /*public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/
    public SQLiteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 当这个SQLiteOpenHelper的子类类被实例化时会创建指定名的数据库，在onCreate中创建个人信息表
         * **/
        db.execSQL("CREATE TABLE IF NOT EXISTS " + U_USER_INFO + "( "
                + "_id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "id VARCHAR,"
                + "email VARCHAR,"
                + "userName VARCHAR, "
                + "phone VARCHAR, "
                + "sex VARCHAR, "
                + "signature VARCHAR, "
                + "home VARCHAR ,"
                + "location VARCHAR,"
                //一键缓存
                + "phoneNumber VARCHAR,"
                + "messageText VARCHAR,"
                + "currentLocation VARCHAR"
                + ")");

    }

    /**
     * 当数据库版本号增加才会调用此方法
     **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + U_USER_INFO);

        onCreate(db);
    }
}