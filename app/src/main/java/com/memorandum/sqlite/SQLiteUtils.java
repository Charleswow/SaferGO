package com.memorandum.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.memorandum.UserInfo;

public class SQLiteUtils {
	public static final String DATABASE_NAME = "zhaochen_memorandum_db";

	public static final String DATETIME = "datetime";
    public static final String CONTENT = "content";
	
		public static DatabaseHelper createDBHelper(Context context) {
			//����һ��DatabaseHelper����
			DatabaseHelper dbHelper = new DatabaseHelper(context,DATABASE_NAME);
			return dbHelper;
		}

		public void insert(DatabaseHelper dbHelper,UserInfo user) {
			//����ContentValues����
			ContentValues values = new ContentValues();
			//��ö����в����ֵ�ԣ����м���������ֵ��ϣ�����뵽��һ�е�ֵ��ֵ��������ݿ⵱�е���������һ��
			values.put("datetime", user.getDatetime());
			values.put("content",user.getContent());
			values.put("alerttime",user.getAlerttime());
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			//����insert�������Ϳ��Խ����ݲ��뵽���ݿ⵱��
			db.insert("user", null, values);
			db.close();
		}
    
		
		//���²������൱��ִ��SQL��䵱�е�update���
		//UPDATE table_name SET XXCOL=XXX WHERE XXCOL=XX...
		public void update(DatabaseHelper dbHelper) {
			
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("content", "zhaochen");
			//��һ��������Ҫ���µı���
			//�ڶ���������һ��ContentValeus����
			//������������where�Ӿ�
			db.update("user", values, "id=?", new String[]{"1"});
			db.close();
		}

		public void delete(DatabaseHelper dbHelper,String datetime){
			
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			// ɾ�������������
			// db.delete("users",null,null);
			// �ӱ���ɾ��ָ����һ������
			db.execSQL("DELETE FROM " + "user" + " WHERE datetime="+ datetime);
			db.close();
		}
}
