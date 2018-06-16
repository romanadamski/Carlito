package com.brentaureli.mariobros.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.brentaureli.mariobros.Tools.MyCallbackListener;
import com.brentaureli.mariobros.android.AndroidLauncher;
import com.brentaureli.mariobros.android.R;

public class MainActivity extends Activity {
    Button bWlaczBluetooth;
    Button bDolaczDoGry;
    Button bUtworzNowaGre;
    Button bMario;
    ServerBluetooth serwer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bWlaczBluetooth=(Button) findViewById(R.id.bWlaczBluetooth);
        bDolaczDoGry=(Button) findViewById(R.id.bDolaczDoGry);
        bUtworzNowaGre=(Button) findViewById(R.id.bUtworzNowaGre);
        bMario=(Button) findViewById(R.id.bMario);
        bWlaczBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wlaczBluetooth();
            }
        });

        bDolaczDoGry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,ListaUrzadzen.class);
                startActivity(intent);
            }
        });
        bUtworzNowaGre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPlainAlertDialog().show();
                //startuje wątek z serwerem
                serwer=new ServerBluetooth();
                if(!serwer.isAlive()){
                    serwer.start();
                }
                //jeśli połączono-startuje aktywnosc z gra, dopoki nie-wyswietla sie dialog z laczeniem i anuluj
                class AsyncSerwerOdbior extends AsyncTask<String,Void, Void> {
                    @Override
                    protected Void doInBackground(String... strings) {
                        Intent intent;
                        while(true){
                            if (serwer.polaczono.equals("Połączono")){
                                Context context;
                                context = getApplicationContext();
                                intent= new Intent(context,AndroidLauncher.class);
                                break;
                            }
                        }
                        intent.putExtra("skin", "KARLITO");
                        startActivity(intent);
                        //polaczenie:
                        while(true){
                            //serwer wysyla
                            serwer.write(Float.toString(MyCallbackListener.sendWsp));
                            //serwer odbiera
                            MyCallbackListener.receiveWsp=Float.parseFloat(serwer.wiadPrzych);
                            System.out.println("serwer odbiera: "+MyCallbackListener.receiveWsp);
                            //sleep zeby sie nie zacinalo

                            if (!serwer.polaczono.equals("Połączono")){
                                break;
                            }
                        }
                        return null;
                    }
                }
                AsyncSerwerOdbior aso = new AsyncSerwerOdbior();
                aso.execute();

            }
        });
        bMario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,AndroidLauncher.class);
                startActivity(intent);

            }
        });
        BluetoothAdapter ba =BluetoothAdapter.getDefaultAdapter();
        if(!ba.isEnabled()){
            Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(i,1);
        }
    }
    private Dialog createPlainAlertDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Łączenie");
        dialogBuilder.setMessage("Poczekaj na połączenie z drugim graczem");
        dialogBuilder.setNegativeButton("Anuluj", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                //po kliknieciu anuluj-serwer przestaje nasluchiwac
            }
        });
        dialogBuilder.setCancelable(false);
        return dialogBuilder.create();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i){
        if(resultCode==Activity.RESULT_OK){
            BluetoothAdapter ba=BluetoothAdapter.getDefaultAdapter();
        }
    }
    void wlaczBluetooth(){
        Intent pokazSie=new Intent (BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        pokazSie.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(pokazSie);
    }
}
