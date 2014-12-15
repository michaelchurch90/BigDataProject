package com.csci8790.registrationlibrary;

import org.json.JSONArray;

/**
 * Java library to get the registrations available from the registration service
 */
public class Register extends AbstractParseConnection {
	
	/**
	 * gets the registrations available from the registration service.
	 * @return	A JSONArray of all of the registrations available.
	 */
	public JSONArray getRegistrations() {
		
		return readParseBatchResponse(openParseConnection("https://api.parse.com/1/classes/Devices","where={\"Show\":true}" ,"limit=1000"));
		
	}
	
}