package carlito.Menu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
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

public class ServerBluetooth extends Thread {
    private BluetoothServerSocket SerwerSocket;
    String wiadWych="0";
    String wiadPrzych="0";
    String polaczono="Nie połączono";
    PrintWriter out;
    volatile boolean running = true;
    public static boolean disconnect;
    public ServerBluetooth(){
        BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        BluetoothServerSocket temp=null;
        try{
            UUID uuid=UUID.fromString("d83eac47-1eea-4654-8eca-74c691c13484");
            temp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Usluga witajaca", uuid);

        }catch(IOException e){}
        SerwerSocket=temp;
    }

    public void run(){
        running=true;
        BluetoothSocket Socket=null;
        while(true) {
            try {
                Socket = SerwerSocket.accept();
                if(Socket.isConnected()){
                    disconnect=false;
                    out = new PrintWriter(Socket.getOutputStream(), true);
                    polaczono="Połączono";
                    break;
                }
            } catch (IOException e) {}
        }
        
        while(true){
            try{
                BufferedReader in=new BufferedReader(new InputStreamReader(Socket.getInputStream()));
                wiadPrzych=in.readLine();
            }catch(IOException e){
                //info o rozlaczeniu
                disconnect=true;
            }
        }
    }
    public void write(String wiadomosc){
        out.println(wiadomosc);
    }
}
