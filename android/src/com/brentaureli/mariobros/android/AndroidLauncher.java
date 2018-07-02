package com.brentaureli.mariobros.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Menu.ClientBluetooth;
import com.brentaureli.mariobros.Menu.ServerBluetooth;
import com.brentaureli.mariobros.Tools.MyCallbackListener;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Bundle extras = getIntent().getExtras();
		String skin="KARLITO";
		if(extras!=null){
			skin = extras.getString("skin");
		}

		MarioBros marioBros=new MarioBros(skin);
		initialize(marioBros, config);
		if(MyCallbackListener.sinlgePlay>-1){
			/*AsyncKlient ak = new AsyncKlient();
			ak.execute();
			if(!ak.isCancelled()){
				disconnectDialog().show();
			}*/
			/*
			ak ak=new ak();
			ak.start();
			*/
			dupa d=new dupa();
			//d.run();
		}
	}

	@Override
	public void onBackPressed() {
		if(MyCallbackListener.result==0)
			backButtonDialog().show();
	}

	private Dialog backButtonDialog() {
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Wyjście");
		dialogBuilder.setMessage("Czy na pewno chcesz wyjść?");
		dialogBuilder.setNegativeButton("Tak", new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				//reset
				Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
		});
		dialogBuilder.setPositiveButton("Nie", new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		dialogBuilder.setCancelable(false);
		return dialogBuilder.create();
	}
	/*
	class AsyncKlient extends AsyncTask<String,Void, Void> {
		@Override
		protected Void doInBackground(String... strings) {
			backButtonDialog().show();
			while(true){
				if(ClientBluetooth.disconnect || ServerBluetooth.disconnect){
					//dialog o rozlaczeniu i "OK" resetuje
					System.out.println("androidlaucher: jestem w ifie");
					disconnectDialog().show();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			}
			//return null;
		}
	}*/
	/*
	class ak extends Thread{
		@Override
		public void run(){
			while(true){
				if(ClientBluetooth.disconnect || ServerBluetooth.disconnect){
					//dialog o rozlaczeniu i "OK" resetuje
					System.out.println("androidlaucher: jestem w ifie");
					break;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			}
			disconnectDialog().show();
		}
	}
	*/
	class dupa implements Runnable{
		public void run(){
			while(true){
				if(ClientBluetooth.disconnect || ServerBluetooth.disconnect){
					Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					android.os.Process.killProcess(android.os.Process.myPid());
					System.exit(0);
					System.out.println("androidlaucher: jestem w ifie");
					break;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			}
			disconnectDialog().show();
		}
	}
	private Dialog disconnectDialog() {
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("AAAAAAAAA");
		dialogBuilder.setMessage("Rozłączyło cię z przeciwnikiem");
		dialogBuilder.setNegativeButton("To pszypau", new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				//reset
				Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
		});
		dialogBuilder.setCancelable(false);
		return dialogBuilder.create();
	}
}
