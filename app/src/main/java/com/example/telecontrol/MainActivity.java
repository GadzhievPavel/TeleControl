package com.example.telecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.telecontrol.view.Custom2Joystick;


public class MainActivity extends AppCompatActivity implements Runnable{
    Thread thread;
    //Custom2Joystick joystickView;
    TextView angleText, stretch, direction;
    DatagramListener datagramListener;
    int input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String ip = getIntent().getStringExtra("ip");
        System.out.println(ip);
        //joystickView = findViewById(R.id.joystick);// объявление элементов интерфейса
        angleText = findViewById(R.id.angle);// объявление элементов интерфейса
        stretch = findViewById(R.id.stretch);// объявление элементов интерфейса
        direction = findViewById(R.id.inputcoomand);
        datagramListener=new DatagramListener(ip,21321);
        //thread = new Thread(this,"input data");
        //thread.start();
//        joystickView.setOnMoveListener(new Custom2Joystick.OnMoveListener() { // обработчик перемещения джойстика
//            @Override
//            public void onMove(int angle, int strength) {
//                byte[] sendPacket = new byte[5];
//                stretch.setText("Stretch"+ strength);
//                angleText.setText("Angle "+ angle);
//                sendPacket[0]= (byte) (angle>>24);//формирование передаваемого пакета
//                sendPacket[1]= (byte) (angle>>16);
//                sendPacket[2]= (byte) (angle>>8);
//                sendPacket[3]= (byte) angle;
//                sendPacket[4]= (byte) strength;
//                datagramListener.setControlMsg(sendPacket); // передача пакета
//                if(datagramListener.getInputData()==1){
//                    direction.setText("direction: "+"Вперед");
//                }else{
//                    direction.setText("direction: "+"Другое направление");
//                }
//            }
//        });
    }

    @Override
    public void run() {
        while (true){
            try {
                input = datagramListener.getInputData();
                if(1 == input){
                    direction.setText("direction: "+"Вперед");
                }
            }catch (Exception e){
                direction.setText("direction: "+"нет ответа");
                System.out.println("Error");
            }
        }
    }
}