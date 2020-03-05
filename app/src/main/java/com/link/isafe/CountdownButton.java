package com.link.isafe;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;



public class CountdownButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {
    /**
     * 倒计时时长，默认倒计时时间60秒；
     */
    private long length = 60 * 1000;
    /**
     * 开始执行计时的类，可以在每秒实行间隔任务
     */
    private Timer timer;
    /**
     * 每秒时间到了之后所执行的任务
     */
    private TimerTask timerTask;
    /**
     * 在点击按钮之前按钮所显示的文字，默认是获取验证码
     */
    private String beforeText = "获取验证码";
    /**
     * 在开始倒计时之后那个秒数数字之后所要显示的字，默认是秒
     */
    private String afterText = "秒";
    /**
     * 按钮点击事件
     */
    private OnClickListener onClickListener;


    public CountdownButton(Context context) {
        super(context);
        initView();
    }

    public CountdownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CountdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化操作
     */
    private void initView() {
        if (!TextUtils.isEmpty(getText())) {
            beforeText = getText().toString().trim();
        }
        this.setText(beforeText);
        setOnClickListener(this);
    }

    /**
     * 初始化时间
     */
    private void initTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
    }

    /**
     * 设置倒计时时长
     *
     * @param length 默认毫秒
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * 设置未点击时显示的文字
     *
     * @param beforeText
     */
    public void setBeforeText(String beforeText) {
        this.beforeText = beforeText;
    }

    /**
     * 设置未点击后显示的文字
     *
     * @param beforeText
     */
    public void setAfterText(String beforeText) {
        this.afterText = afterText;
    }

    /**
     * 设置监听按钮点击事件
     *
     * @param onclickListener
     */
    @Override
    public void setOnClickListener(OnClickListener onclickListener) {
        if (onclickListener instanceof CountdownButton) {
            super.setOnClickListener(onclickListener);
        } else {
            this.onClickListener = onclickListener;
        }
    }

    /**
     * 点击按钮后的操作
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        start();
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    /**
     * 开始倒计时
     */
    public void start() {
        initTimer();
        this.setText(length / 1000 + afterText);
        this.setEnabled(false);
        timer.schedule(timerTask, 0, 1000);
    }


    /**
     * 更新显示的文本
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CountdownButton.this.setText(length / 1000 + afterText);
            length -= 1000;
            if (length < 0) {
                CountdownButton.this.setEnabled(true);
                CountdownButton.this.setText(beforeText);
                clearTimer();
                length = 60 * 1000;
            }
        }
    };

    /**
     * 清除倒计时
     */
    private void clearTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 记得一定要在activity或者fragment消亡的时候清除倒计时，
     * 因为如果倒计时没有完的话子线程还在跑，
     * 这样的话就会引起内存溢出
     */
    @Override
    protected void onDetachedFromWindow() {
        clearTimer();
        super.onDetachedFromWindow();
    }
}
