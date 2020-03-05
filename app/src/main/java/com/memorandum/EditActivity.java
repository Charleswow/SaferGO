package com.memorandum;

/**
 * Created by joe on 2017/7/1.
 */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.isafety.R;
import com.memorandum.sqlite.DatabaseHelper;
import com.memorandum.sqlite.SQLiteUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class EditActivity extends Activity {

    private String alerttime = "";
    private String datetime;
    private String content;
    private String tempContent,tempDatetime1,tempDatetime,tempAlerttime;
    private int index=0;
    private UserInfo user;
    private TimeSetDialog timeSetDialog=null;
    private Button backButton,timeSetButton;
    private TextView datetext,alertTextView;
    private EditText edittext;
    Calendar calendar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minibook_edit_activity);

        backButton = (Button)findViewById(R.id.backButton);
        timeSetButton = (Button)findViewById(R.id.timeSet);
        datetext = (TextView)findViewById(R.id.dateText);
        edittext = (EditText)findViewById(R.id.editText);
        alertTextView = (TextView)findViewById(R.id.timeText);

        user = new UserInfo();
        user.setAlerttime(alerttime);
        timeSetButton.setOnClickListener( new OnClickListener(){
            @Override
            public void onClick(View v) {
                timeSetDialog = new TimeSetDialog(EditActivity.this);
                //添加监听器，当dialog消失即执行cancel()方法时触发的事件
                timeSetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        alerttime = timeSetDialog.alerttime;
                        if(alerttime != null)
                            alertTextView.setText(Utils.timeTransfer(alerttime));
                        else
                            alertTextView.setText("");
                        calendar = timeSetDialog.calendar;
                        user.setAlerttime(alerttime);
                    }
                });
                timeSetDialog.show();
            }
        });

        backButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("android.intent.extra.INTENT");
        datetime = bundle.getString("datetime");
        content = bundle.getString("content");
        alerttime = bundle.getString("alerttime");
        index = bundle.getInt("index");
        tempContent = new String(content);
        tempDatetime = new String(datetime);
        tempAlerttime = new String(alerttime);
        Time time = new Time();
        //判断该记录是新建还是修改
        if(datetime.equals(""))
        {
            time.setToNow();
        }
        else{
            time.set(Long.parseLong(datetime));
        }
        int month = time.month+1;
        int day = time.monthDay;
        int hour = time.hour;
        int minute = time.minute;
//		String minute = time.minute<10?"0"+time.minute:String.valueOf(time.minute);
        tempDatetime1 = month+"月"+day+"日"+'\n'+Utils.format(hour)+":"+Utils.format(minute);
        datetext.setText(tempDatetime1);
        edittext.setText(content);
        String tempS = new String(alerttime);
        if(!alerttime.equals(""))
            alertTextView.setText(Utils.timeTransfer(tempS));
        else alertTextView.setText("");
        edittext.setSelection(content.length());  //设置光标在文字末尾
    }
    //时钟设置监听器类
//	class TimeSetListener implements TimePickerDialog.OnTimeSetListener{
//		@Override
//		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//			TextView alertTextView = (TextView)findViewById(R.id.timeText);
//			calendar.setTimeInMillis(System.currentTimeMillis());
//		    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//		    calendar.set(Calendar.MINUTE, minute);
//		    calendar.set(Calendar.SECOND, 0);
//		    calendar.set(Calendar.MILLISECOND, 0);
//		    //需要一个时间判断方法，根据时间大小灵活显示
//		    tempAlrettime = Utils.format(hourOfDay) + ":" +Utils.format(minute);
//		    alerttime = tempAlrettime;
//		    user.setAlerttime(alerttime);
//		    alertTextView.setText(tempAlrettime);
//		}
//	}


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        edittext = (EditText)findViewById(R.id.editText);
        Time time = new Time();
        time.setToNow();

        user.setAlerttime(alerttime);
        datetime =""+time.toMillis(true);
        user.setDatetime(datetime);
        time.set(time.toMillis(true));

        content = edittext.getText().toString();
        user.setContent(content);

        if((!content.isEmpty() && !tempContent.equals(content)) || !alerttime.equals("") && !alerttime.equals(tempAlerttime)){                   //如果内容非空且已经被更改，则更新显示和数据库
            ArrayList<HashMap<String,String>> list = Utils.getList();
            SQLiteUtils sqlite = new SQLiteUtils();
            System.out.println("---------------------------");
            DatabaseHelper dbHelper = sqlite.createDBHelper(EditActivity.this);
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("datetime",user.getDatetime());
            map.put("content",user.getContent());
            map.put("alerttime",user.getAlerttime());

            if(tempContent.isEmpty())  {					//若为新建记录则添加
                list.add(map);
                sqlite.insert(dbHelper,user);
            }
            else {
                list.set(index, map);          //若为修改替换掉原来的记录
                sqlite.delete(dbHelper, tempDatetime);
                sqlite.insert(dbHelper,user);
            }
            //设置闹钟提醒
            if(!alerttime.equals(tempAlerttime) && !alerttime.equals(""))
            {
                //System.out.println("alerttime done!");
                Toast.makeText(this, "设定成功", Toast.LENGTH_SHORT).show();
                /*Intent intent=new Intent(EditActivity.this,AlarmActivity.class);
                startActivity(intent);*/
                alertSet();
            }

        }
    }


    private void alertSet(){
        Intent intent = new Intent("android.intent.action.ALARMRECEIVER");
        intent.putExtra("datetime", datetime);
        intent.putExtra("content", content);
        intent.putExtra("alerttime",alerttime);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(EditActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);
        //setRepeating()这里第二个参数不能设置成现在时间，否则闹钟会设置完就开启
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),(24 * 60 * 60 * 1000), pendingIntent);
    }
}