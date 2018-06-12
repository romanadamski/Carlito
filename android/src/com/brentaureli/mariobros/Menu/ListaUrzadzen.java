package com.brentaureli.mariobros.Menu;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.brentaureli.mariobros.android.AndroidLauncher;
import com.brentaureli.mariobros.android.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by romek95a on 14.05.2018.
 */

public class ListaUrzadzen extends Activity{
    private ListView lv;
    int j, rozmiar;
    String[] urzadzeniaTab=new String[20];
    String[] adresyTab=new String[20];

    Vector<String> urzadzenia=new Vector();
    List<String> adresy=new ArrayList();

    private void initUrzadzeniaListView(){
        lv.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1,urzadzeniaTab));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id){
                //pobranie adresu MAC:
                Log.d("Po wybraniu urz.:","Urzadzenie: "+urzadzeniaTab[pos]+", adres: "+adresyTab[pos]);
                BluetoothAdapter ba=BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice server=ba.getRemoteDevice(adresyTab[pos]);
                final ClientBluetooth klient=new ClientBluetooth(server);
                klient.start();
                //jeśli połączono-startuje aktywnosc z gra
                //na wontku

                class AsyncSerwerOdbior extends AsyncTask<String,Void, Void> {
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
                        String klientJson = new Gson().toJson(klient);
                        intent.putExtra("klient", klientJson);
                        startActivity(intent);
                        return null;
                    }
                }
                AsyncSerwerOdbior aso = new AsyncSerwerOdbior();
                aso.execute();
            }
        });
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_urzadzen);
        j=0;
        wykryjInne();
        lv = (ListView) findViewById(R.id.list);
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
                urzadzeniaTab[j]=nazwaPara;
                adresyTab[j]=device.getAddress();
                Log.d("Znalazlem",nazwaPara+", "+device.getAddress());
                j++;
                initUrzadzeniaListView();
            }
        }

    };
    void wykryjInne(){
        for(int i=0;i<20;i++){
            urzadzeniaTab[i]="";
            adresyTab[i]="";
        }
        IntentFilter iFiltr=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(odbiorca, iFiltr);
        BluetoothAdapter ba=BluetoothAdapter.getDefaultAdapter();
        ba.startDiscovery();
    }
}
