package com.example.setiatmobile;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;


public class BackgroundWork extends IntentService {

	public BackgroundWork()
	{
		super("BackgroundWork");
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		//PowerManager mgr = (PowerManager)getSystemService(Context.POWER_SERVICE);
		//WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
		//wakeLock.acquire();
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle("App Running")
		.setContentText("App Running")
		.setOngoing(true);
		
		
		
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		String batteryPercent = sharedPref.getString("pref_batteryCharge","50");
		boolean wifiOnly = sharedPref.getBoolean("pref_wifionly", true);
		boolean enabled = sharedPref.getBoolean("pref_enabled", true);
		boolean chargeOnly = sharedPref.getBoolean("pref_chargeonly", false);
	

		
		
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus= registerReceiver(null, ifilter);
		int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
		boolean isCharging = status==BatteryManager.BATTERY_STATUS_CHARGING ||
								status==BatteryManager.BATTERY_STATUS_FULL;
		int level =batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
		float pct = level/ (float)scale;
		
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isWifi = activeNetwork.getType()== ConnectivityManager.TYPE_WIFI;
		
		
		if(enabled && 
				(!wifiOnly|| isWifi) && 
				(!chargeOnly || isCharging) &&
				((pct*100)>=Integer.parseInt(batteryPercent)))
		{

			mNotificationManager.notify(0,mBuilder.build());
			
			//Do Work Here------------------------
			try
			{
				Processor imageProcessor = new Processor("target");
				JSONObject jObject = new JSONObject("http://i.imgur.com/LskVyQi.png");
				JSONObject returnObject;
				returnObject = imageProcessor.processImage(jObject); // Will not work, needs to be passed a JSONObject containing relevant data.
				
			}
			catch(Exception e){}
			
			
			//------------------------------------
			
    		//Run the intent again after the previous task finished
    		startService(intent);
		}
		else
		{
			mNotificationManager.cancel(0);
		}
		//wakeLock.release();

		

	}

}
