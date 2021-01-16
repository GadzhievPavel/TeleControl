package com.example.telecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class StartActivity extends AppCompatActivity {
    Button button;
    TextView myIP;
    EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_layout);
        button = findViewById(R.id.button); // объявление элементов интерфейса
        myIP = findViewById(R.id.ip);// объявление элементов интерфейса
        editText = findViewById(R.id.edit_ip_robot);// объявление элементов интерфейса
        String ip="Мой IP ";
        try { //нахождение ip адресов устройства
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp()) //отсеивание локальных интерфейсов и выключенных
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if(addr.getHostAddress().contains("192.")){//нахождение адресов соответствующим локальным сетям
                        ip += addr.getHostAddress()+"; ";
                    }
                    System.out.println(iface.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        myIP.setText(ip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//обработчик нажатия для перехода на следующий экран
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                Toast.makeText(StartActivity.this,editText.getText(),Toast.LENGTH_LONG);
                intent.putExtra("ip",editText.getText().toString());//передача ip сервера
                startActivity(intent);
            }
        });
    }

}
