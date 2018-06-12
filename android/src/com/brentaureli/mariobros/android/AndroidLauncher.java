package com.brentaureli.mariobros.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Menu.ClientBluetooth;
import com.brentaureli.mariobros.Menu.ServerBluetooth;
import com.google.gson.Gson;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Bundle extras = getIntent().getExtras();
		String skin="KARLITO";
		if(extras!=null){
			skin = extras.getString("skin");
			String serwerJson=extras.getString("serwer");
			ServerBluetooth serwer=new Gson().fromJson(serwerJson, ServerBluetooth.class);
			String klientJson=extras.getString("klientJson");
			ClientBluetooth klient=new Gson().fromJson(serwerJson, ClientBluetooth.class);
		}

		MarioBros marioBros=new MarioBros(skin);

		initialize(marioBros, config);
	}
}
