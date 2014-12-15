package com.csci8790.registrationlibrary;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings.Secure;

/**
 * The Android registration service that registers a target device as being able to process data.
 *
 */
public class Register {

	private static final String PARSE_APP_ID = "SZQbdw4Gk4LW0vS4Qwcc56klXTzSgvVR9ex0YsUR";
	private static final String PARSE_CLIENT_KEY = "qMzB73EcFy4mCGyurqb4pC2ChmPH2fabP8KShbH4";
	
	public static String mParseDeviceId;
	
	/**
	 * Registers the device with the registration service.
	 * @return	Whether the registration was successful.
	 */
	public boolean register(Context context) {
		
		Parse.initialize(context, PARSE_APP_ID, PARSE_CLIENT_KEY);
		
		ParseObject registration = new ParseObject("Devices");
		registration.put("androidId", Secure.ANDROID_ID);
		registration.saveInBackground();
		
		if (registration.get("objectId") != null) {
			mParseDeviceId = registration.get("objectId").toString();
			return true;
		}
		else {
			return false;
		}
	}
}