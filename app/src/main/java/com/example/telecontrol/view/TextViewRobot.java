package com.example.telecontrol.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TextViewRobot extends androidx.appcompat.widget.AppCompatTextView
{

    private static final int DEFAULT_LOOP_INTERVAL = 50;
    private Custom2Joystick.OnMoveListener mCallback;
    private long mLoopInterval = DEFAULT_LOOP_INTERVAL;

    public TextViewRobot(Context context) {
        super(context);
    }

    public void setOnMoveListener(Custom2Joystick.OnMoveListener l) {
        setOnMoveListener(l, DEFAULT_LOOP_INTERVAL);
    }

    public void setOnMoveListener(Custom2Joystick.OnMoveListener l, int loopInterval) {
        mCallback = l;
        mLoopInterval = loopInterval;
    }
}
