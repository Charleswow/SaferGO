package com.memorandum.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
		super(context, name, factory, version);
		
	}
	
	public DatabaseHelper(Context context, String name, int version){
		this(context,name,null,version);
	}
	
	public DatabaseHelper(Context context, String name){
		this(context,name,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//ʹ��execSQL����ִ��SQL���
		db.execSQL("create table user(datetime varchar(30),content varchar(100),alerttime varchar(30))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
