package com.hqs.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 2016/10/25.
 */

public class TimerButton extends RelativeLayout {

    private int backgroundColorNormal = -1;
    private int backgroundColorDisable = -1;
    private String titleNormal;
    public int totalTime = -1;
    private int curTime = -1;

    private Context context;
    private TextView titleView;
    private Timer timer;

    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setup();


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimerButton);
        int titleColor = a.getColor(R.styleable.TimerButton_titleColor, Color.BLUE);
        totalTime = a.getInteger(R.styleable.TimerButton_totalTime, 60);
        titleNormal = a.getString(R.styleable.TimerButton_titleNormal);
        backgroundColorNormal = a.getColor(R.styleable.TimerButton_backgroundColorNormal, Color.YELLOW);
        backgroundColorDisable = a.getColor(R.styleable.TimerButton_backgroundColorDisable, Color.GRAY);

        a.recycle();

        titleView.setTextColor(titleColor);
        titleView.setText(titleNormal);
        this.setBackgroundColor(backgroundColorNormal);
    }

    public TimerButton(Context context) {
        super(context);
        this.context = context;
        setup();
    }

    private void setup(){
        titleView = new TextView(context);
        this.addView(titleView);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT, TRUE);
        titleView.setLayoutParams(params);
    }

    public void startTimer(){

        if (timer == null){
            setBackgroundColor(backgroundColorDisable);
            timer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (curTime < 0){
                        curTime = totalTime;
                    }
                    if (curTime == 0){
                        reset();
                    }
                    else{
                        final String text = curTime + "s";
                        titleView.post(new Runnable() {
                            @Override
                            public void run() {
                                setTitle(text.toLowerCase());
                            }
                        });
                    }
                    curTime --;
                }
            };
            timer.schedule(task, 0, 1000);
        }
    }

    public void setBackgroundColorNormal(int backgroundColorNormal) {
        this.backgroundColorNormal = backgroundColorNormal;
    }

    public void setBackgroundColorDisable(int backgroundColorDisable) {
        this.backgroundColorDisable = backgroundColorDisable;
    }

    public String getTitleNormal() {
        return titleNormal;
    }

    public void setTitleNormal(String titleNormal) {
        this.titleNormal = titleNormal;
    }

    public void setTitle(String title){
        titleView.setText(title);
    }

    public void stop(){
        if (timer != null) {
            reset();
        }
    }

    private void reset(){
        timer.cancel();
        timer = null;
        curTime = -1;
        setBackgroundColor(backgroundColorNormal);
        setTitle(titleNormal);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (curTime < 0){
            return super.onTouchEvent(event);
        }
        else{
            return true;
        }
    }
}
