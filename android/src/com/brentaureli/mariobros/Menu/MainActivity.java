package com.brentaureli.mariobros.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.badlogic.gdx.ApplicationAdapter;
import com.brentaureli.mariobros.android.AndroidLauncher;
import com.brentaureli.mariobros.android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class MainActivity extends Activity {
    Button bWlaczBluetooth;
    Button bDolaczDoGry;
    Button bUtworzNowaGre;
    Button bMario;
    final ServerBluetooth serwer=new ServerBluetooth();
    private TextView klientserwer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bWlaczBluetooth=(Button) findViewById(R.id.bWlaczBluetooth);
        bDolaczDoGry=(Button) findViewById(R.id.bDolaczDoGry);
        bUtworzNowaGre=(Button) findViewById(R.id.bUtworzNowaGre);
        bMario=(Button) findViewById(R.id.bMario);
        klientserwer=(TextView) findViewById(R.id.klientserwer);
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
                if(!serwer.isAlive()){
                    serwer.start();
                }
                //jeśli połączono-startuje aktywnosc z gra, dopoki nie-wyswietla sie dialog z laczeniem i anuluj
                if (serwer.polaczono.equals("Połączono")){
                    Context context;
                    context = getApplicationContext();
                    Intent intent = new Intent(context,AndroidLauncher.class);
                    startActivity(intent);
                }

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
