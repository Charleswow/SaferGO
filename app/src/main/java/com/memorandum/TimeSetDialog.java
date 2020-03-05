package com.memorandum;

/**
 * Created by joe on 2017/7/1.
 */


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.jack.isafety.R;

import java.util.Calendar;

public class TimeSetDialog extends Dialog {

    Button dateSetButton,positiveButton,negativeButton;
    TimePicker timePicker;
    Calendar calendar;
    String date,alerttime=null;
    private TimeSetDialog timeSetDialog = null;

    //初始化时间设置
    private void init(){
        calendar.setTimeInMillis(System.currentTimeMillis());
        dateSetButton.setText(Utils.toDateString(calendar));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }
    public TimeSetDialog(final Context context) {
        super(context);
        setContentView(R.layout.minibook_timeset_view);

        timeSetDialog = this;
        this.setTitle("设置时间提醒我");

        calendar = Calendar.getInstance();
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        dateSetButton = (Button)findViewById(R.id.dateButton);
        positiveButton = (Button)findViewById(R.id.positiveButton);
        negativeButton = (Button)findViewById(R.id.negativeButton);

        init();

        dateSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //设置日期
                        calendar.set(year, monthOfYear, dayOfMonth);
                        date = Utils.toDateString(calendar);
                        dateSetButton.setText(date);
                    }}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                alerttime = calendar.getTimeInMillis()+"";

                timeSetDialog.cancel();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSetDialog.cancel();
            }
        });
    }
}
