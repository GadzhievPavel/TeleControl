package com.example.telecontrol;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class DatagramListener implements Runnable {
    private Thread thread;
    private DatagramSocket udpSocket;
    private DatagramPacket inputData;
    private Handler handler;
    private byte[] controlMsg = new byte[2];
    private String ip;
    private  int port;
    private int input=0;
    boolean run;

    public DatagramListener(String ip, int port){//установка адреса сервера и порта
        thread = new Thread(this,"sending data thread");
        this.ip=ip;
        this.port=port;
        thread.start();//передача сокетов работает в отдельном потоке
    }
    @Override
    public void run() {
        try {
            run=true;
            InetAddress inetAddress = InetAddress.getByName(ip);
            udpSocket = new DatagramSocket(port);
            byte [] startMsg = ("start").getBytes();
            DatagramPacket packet = new DatagramPacket(startMsg,startMsg.length,inetAddress,port);
            byte [] data = new byte[1];

            udpSocket.send(packet);
            Log.d("Connect","OK");
            while (run){
                try{
                    inputData = new DatagramPacket(data,data.length,inetAddress,port);
                    udpSocket.setSoTimeout(1);
                    controlMsg = getControlMsg();
                    DatagramPacket packetSend= new DatagramPacket(controlMsg,
                            controlMsg.length,inetAddress,port);
                    udpSocket.send(packetSend);
                    udpSocket.receive(inputData);
                    input = Integer.valueOf(inputData.getData()[0]);
                    Log.e("Input Data", String.valueOf(Integer.valueOf(inputData.getData()[0])));
                } catch (SocketTimeoutException e) {
                    Log.e("Timeout","SocketTimeoutException"+udpSocket.getSoTimeout());
                    e.printStackTrace();
                    //udpSocket.close();
                }catch (SocketException e) {
                    Log.e("Socket Open","Error",e);
                    //udpSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //геттер и сеттер для передачи отправляемого пакета
    public byte[] getControlMsg() {
        return controlMsg;
    }

    public void setControlMsg(byte[] controlMsg) {
        this.controlMsg = controlMsg;
    }

    public int getInputData(){
            return input;
    }
}
