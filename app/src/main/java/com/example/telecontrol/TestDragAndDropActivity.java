package com.example.telecontrol;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.telecontrol.view.ControlView;
import com.example.telecontrol.view.Custom2Joystick;
import com.example.telecontrol.view.MyCustomJoystic;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class TestDragAndDropActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener, Custom2Joystick.OnMoveListener {

    ImageView imageView;
    RelativeLayout layout;
    boolean enableDrag = false;
    TextView joystickText;
    TextView showText;
    EditText name;
    EditText topic;
    Button create;
    RelativeLayout relativeLayout;
    CheckBox checkBox;
    ArrayList<ControlView> viewList;
    CoordinatorLayout coordinatorLayout;
    DialogFragment dialogFragment;
    FragmentManager fragmentManager;
    private int _xDelta, _yDelta;
    private int indexView;
    int typeView = -1;//0- Joystic 1 - TextView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_drop_layout);
        indexView =0;
        viewList = new ArrayList<>();
        coordinatorLayout = findViewById(R.id.lay);
        joystickText = findViewById(R.id.joystick_text);
        showText = findViewById(R.id.show_text);
        checkBox = findViewById(R.id.cheak_edit);
        create = findViewById(R.id.create);
        relativeLayout = findViewById(R.id.rel_but);
        create.setOnClickListener(this);
        name = findViewById(R.id.name_obj);
        topic = findViewById(R.id.topic_obj);
        checkBox.setOnCheckedChangeListener(this);
        showText.setOnClickListener(this);
        joystickText.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.joystick_text:
                create.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                topic.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                typeView = 0;
                break;
            case R.id.show_text:
                create.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                topic.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                typeView = 1;
                break;
            case R.id.create:
                if(!(name.getText().length()>0 && topic.getText().length()>0)){
                    Toast.makeText(this, "Не хватает данных", Toast.LENGTH_SHORT).show();
                }else if (typeView == 0){
                    LinearLayout.LayoutParams params;
                    Custom2Joystick joystickView = new Custom2Joystick(TestDragAndDropActivity.this);
                    //MyCustomJoystic joystickView = new MyCustomJoystic(TestDragAndDropActivity.this, name.getText().toString());
                    params = new LinearLayout.LayoutParams(250,250);
                    joystickView.setBorderColor(Color.argb(100,50,50,200));
                    joystickView.setBackgroundColor(Color.argb(20,20,20,40));
                    joystickView.setLayoutParams(params);
                    joystickView.setName(name.getText().toString());
                    joystickView.setTopic(topic.getText().toString());
                    joystickView.setOnTouchListener(this);
                    joystickView.setOnMoveListener(this);
                    ControlView<View> controlView = new ControlView<>(joystickView);
                    controlView.setTopic(topic.getText().toString());
                    controlView.setName(name.getText().toString());
                    coordinatorLayout.addView(joystickView,indexView++);
                    viewList.add(controlView);
                    topic.setVisibility(View.GONE);
                    name.setVisibility(View.GONE);
                    create.setVisibility(View.GONE);
                    topic.getText().clear();
                    name.getText().clear();
                }else if(typeView == 1){
                    TextView textView1 = new TextView(TestDragAndDropActivity.this);
                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(350,250);
                    params1.setMargins(100,100,100,100);
                    textView1.setText("name:0.0");
                    textView1.setTextSize(18);
                    textView1.setLayoutParams(params1);
                    textView1.setOnTouchListener(this);
                    coordinatorLayout.addView(textView1, indexView++);
                    ControlView<TextView> view = new ControlView<>(textView1);
                    view.setTopic(topic.getText().toString());
                    view.setName(name.getText().toString());
                    viewList.add(view);
                    topic.setVisibility(View.GONE);
                    name.setVisibility(View.GONE);
                    create.setVisibility(View.GONE);
                    topic.getText().clear();
                    name.getText().clear();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(!isChecked){
            enableJoysticks(true);
        }else{
            enableJoysticks(false);
        }
    }

    private void enableJoysticks(boolean b){
        this.enableDrag = !b;
        for (int i = 0; i<viewList.size();i++) {
            if(viewList.get(i).getView() instanceof Custom2Joystick){
                Custom2Joystick joystickView = (Custom2Joystick) viewList.get(i).getView();
                joystickView.setEnabled(b);
            }
            if(viewList.get(i).getView() instanceof TextView){
                TextView text = (TextView) viewList.get(i).getView();
                //text.setEnabled(b);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("touch", "event X: "
                +event.getX()+" Y: "+event.getY()+" view X: "+v.getX()+" Y: "+v.getY());
        Log.d("display","Width "+getDisplay().getWidth()+" height "+getDisplay().getHeight());
        /*if(enableDrag){
            switch (event.getAction()){
                case MotionEvent.ACTION_MOVE:
                    float yPosition = v.getY() + event.getY()-(v.getHeight()/2);
                    float xPosition = v.getX() + event.getX()-(v.getWidth()/2);
                    if(getApplicationContext().getDisplay().getHeight()>yPosition ||
                    getApplicationContext().getDisplay().getWidth()>xPosition){
                        v.setY(yPosition);
                        v.setX(xPosition);
                    }
                    break;
            }
        }*/
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        if(enableDrag) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    CoordinatorLayout.LayoutParams lParams = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    v.setLayoutParams(layoutParams);
                    break;
//                default:
//                    return false;
            }
            return true;

        }else{
            return false;
        }


    }

    @Override
    public void onMove(int angle, int strength, String name, String topic) {
        Log.d("Test log","angle "+angle+" strenght "+ strength+" name "+name+" topic "+topic);
    }
}
