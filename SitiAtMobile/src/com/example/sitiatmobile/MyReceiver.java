package com.example.sitiatmobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		//PowerManager mgr = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
		//WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
		//wakeLock.acquire();
        Intent backgroundWork = new Intent(context, BackgroundWork.class);
        context.startService(backgroundWork);       
        //wakeLock.release();

	}

}
