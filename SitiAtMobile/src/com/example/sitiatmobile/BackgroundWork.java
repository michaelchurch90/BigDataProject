package com.example.sitiatmobile;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;


public class BackgroundWork extends IntentService {

	public BackgroundWork()
	{
		super("BackgroundWork");
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		try
		{
			Thread.sleep(5000);
			
			
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
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle("App Running")
			.setContentText("Is charging = "+isCharging+"\n: "+pct+"%\nWiFi: "+isWifi);
			
			
			
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(0,mBuilder.build());
		}
		catch(Exception e)
		{
			
		}
		

	}

}
