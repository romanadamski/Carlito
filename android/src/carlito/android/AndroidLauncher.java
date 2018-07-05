package carlito.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import carlito.CarlitoEscape;
import carlito.Tools.MyCallbackListener;

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

		CarlitoEscape carlitoEscape =new CarlitoEscape(skin);
		initialize(carlitoEscape, config);
		if(MyCallbackListener.sinlgePlay>-1){

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
	private Dialog disconnectDialog() {
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Problem z połączeniem");
		dialogBuilder.setMessage("Zostałeś rozłączony z drugim użytkownikiem");
		dialogBuilder.setNegativeButton("Wróć do menu", new Dialog.OnClickListener() {
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
