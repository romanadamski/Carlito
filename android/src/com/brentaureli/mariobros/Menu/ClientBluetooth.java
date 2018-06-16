package com.brentaureli.mariobros.Menu;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Created by romek95a on 14.05.2018.
 */

public class ClientBluetooth extends Thread {
    private BluetoothSocket Socket;
    private BluetoothDevice Device;
    String wiadWych="0";
    String wiadPrzych="0";
    String polaczono="Nie połączono";
    PrintWriter out;
    public ClientBluetooth(BluetoothDevice device){
        BluetoothSocket temp = null;
        Device = device;
        try{
            UUID uuid=UUID.fromString("d83eac47-1eea-4654-8eca-74c691c13484");
            temp=device.createRfcommSocketToServiceRecord(uuid);

        }catch(Exception e){}
        Socket =temp;
    }
    public void run(){
        try{
            Socket.connect();
            if(Socket.isConnected()){
                polaczono="Połączono";
            }
            Log.d("Info","Polaczylem sie");
            out = new PrintWriter(Socket.getOutputStream(), true);

        }catch (Exception e){

        }
        while(true){
            try{
                BufferedReader in=new BufferedReader(new InputStreamReader(Socket.getInputStream()));
                wiadPrzych=in.readLine();
                Log.d("Klient",wiadPrzych);
            }catch (Exception e){

            }
        }
    }
    public void write(String wiadomosc){
        out.println(wiadomosc);
        Log.d("Write klient",""+wiadomosc);
    }
}
