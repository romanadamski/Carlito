package com.brentaureli.mariobros.Menu;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.brentaureli.mariobros.Tools.MyCallbackListener;
import com.brentaureli.mariobros.android.AndroidLauncher;
import com.brentaureli.mariobros.android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by romek95a on 14.05.2018.
 */

public class ListaUrzadzen extends Activity{
    private ListView lv;
    ArrayList<String> listOfUsers;
    List<String> listOfMacs;
    private CustomAdapter customAdapter;

    private void initUrzadzeniaListView(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id){
                //pobranie adresu MAC:
                BluetoothAdapter ba=BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice server=ba.getRemoteDevice(listOfMacs.get(pos));
                final ClientBluetooth klient=new ClientBluetooth(server);
                klient.start();
                //jeśli połączono-startuje aktywnosc z gra
                //na wontku

                class AsyncKlient extends AsyncTask<String,Void, Void> {
                    @Override
                    protected Void doInBackground(String... strings) {
                        Intent intent;
                        while(true){
                            if (klient.polaczono.equals("Połączono")){
                                Context context;
                                context = getApplicationContext();
                                intent= new Intent(context,AndroidLauncher.class);
                                break;
                            }
                        }
                        intent.putExtra("skin", "KARLITO2");
                        startActivity(intent);
                        //polaczenie:
                        while(true){
                            //klient wysyla
                            klient.write(Float.toString(MyCallbackListener.sendWsp));
                            //klient odbiera
                            MyCallbackListener.receiveWsp=Float.parseFloat(klient.wiadPrzych);


                            if(ClientBluetooth.disconnect || ServerBluetooth.disconnect){
                                Intent intent2 = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
                                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent2);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(0);
                            }

                            if(MyCallbackListener.end==1){
                                Intent intent2 = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
                                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent2);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(0);
                                break;
                            }
                        }
                        return null;
                    }
                }
                AsyncKlient ak = new AsyncKlient();
                ak.execute();
            }
        });
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_urzadzen);
        listOfUsers=new ArrayList<>();
        listOfMacs=new ArrayList<>();
        customAdapter=new CustomAdapter(this, listOfUsers);
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(customAdapter);
        initUrzadzeniaListView();
        wykryjInne();
    }

    @Override
    protected void onStop(){
        try {
        unregisterReceiver(odbiorca);
        } catch (IllegalArgumentException ex) {
        // If Receiver not registered
        }
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    private final BroadcastReceiver odbiorca = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String a=intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(a)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String para="";
                if(device.getBondState()!=BluetoothDevice.BOND_BONDED){
                    para="niesparowane";
                }
                else{
                    para="sparowane";
                }
                String nazwaPara=device.getName()+", "+para;
                customAdapter.add(nazwaPara);
                listOfMacs.add(device.getAddress());
            }
        }

    };
    void wykryjInne(){
        IntentFilter iFiltr=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(odbiorca, iFiltr);
        BluetoothAdapter ba=BluetoothAdapter.getDefaultAdapter();
        ba.startDiscovery();
    }
    private Dialog createBluetoothMessageDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Bluetooth nie działa");
        dialogBuilder.setMessage("Może najpierw włącz bluetooth, co?");
        dialogBuilder.setNegativeButton("OK", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                //w sumie to nic nie musi robić
            }
        });
        dialogBuilder.setCancelable(false);
        return dialogBuilder.create();
    }
}
