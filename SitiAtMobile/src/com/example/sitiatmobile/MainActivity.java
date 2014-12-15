package com.example.sitiatmobile;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;


public class MainActivity extends PreferenceActivity  implements OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new Preferences()).commit();
        
        boolean alarmUp = (PendingIntent.getBroadcast(this, 0, 
                new Intent("com.example.sitiatmobile.AlarmReceiver"), 
                PendingIntent.FLAG_NO_CREATE) != null);
        if(!alarmUp)
        {
        	Toast.makeText(this, "Alarm Service started", Toast.LENGTH_SHORT).show();
        	Intent alarmService = new Intent(this,AlarmReceiver.class);
        	startService(alarmService);
        }

    }
    
    @Override
    protected void onResume()
    {
    	super.onResume();
    	//getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    	if(!isMyServiceRunning(BackgroundWork.class))
    	{
    		Intent intent = new Intent(this, BackgroundWork.class);
    		startService(intent);
    	}
    	else
    	{
    		Toast.makeText(this, "Service already running", Toast.LENGTH_LONG).show();
    	}

    	
    }
    
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
 

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, BackgroundWork.class);
		startService(intent);
		
	}


  
}