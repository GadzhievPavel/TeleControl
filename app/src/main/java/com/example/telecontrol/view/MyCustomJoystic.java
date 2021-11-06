package com.example.telecontrol.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.example.telecontrol.R;
import com.example.telecontrol.dialog.DialogTelecontrolView;

public class MyCustomJoystic extends Custom2Joystick implements DialogTelecontrolView.OnButtonClick {

    private Paint mPaintText;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    int backgroundColor;
    String text ="";


    public MyCustomJoystic(Context context, String name) {
        super(context);
        this.text = name;
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.argb(255,0,0,0));
        mPaintText.setStyle(Paint.Style.FILL);
    }

    public MyCustomJoystic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(backgroundColor);
        mPaintText.setStyle(Paint.Style.FILL);
        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.JoystickView,
                0, 0
        );
        backgroundColor = styledAttributes.getColor(R.styleable.JoystickView_JV_backgroundColor, DEFAULT_BACKGROUND_COLOR);

    }

    public MyCustomJoystic(Context context, AttributeSet attrs) {
        super(context,attrs);
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(backgroundColor);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setTextSize(150);
    }

    private static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                            String text) {

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text==null?"null":text, 0, text==null?4:text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTextSizeForWidth(mPaintText,50,text);
        canvas.drawText(text==null?"text":text,20,30, mPaintText);
    }

    @Override
    public void onDialogClickListener(String name, String topic) {
        Log.d("MJV",name);
        setText(name);
    }
}
