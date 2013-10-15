/*
 * Project		Week_1
 * 
 * Package		com.Cory.week_1
 * 
 * @Author		Cory Green
 * 
 * Date			Oct 3, 2013
 */
package com.Cory.week_3;

import java.net.MalformedURLException;
import java.net.URL;

import com.Cory.lib.WebInfo;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/* The point of this service call is to basically load my json data into
 * storage behind the scenes
 */

public class JSONWeatherService extends IntentService{

	public static final String NAME = "messenger";
	public static final String KEY = "key";
	
	public JSONWeatherService() {
		super("JSONWeatherService");
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		/* this has to do with my messenger from the main activity */
		Bundle extras = intent.getExtras();
		
		/* loading in the passed in name of the band we wish to get more info on. */
		Messenger messenger = (Messenger) extras.get(NAME);
		String keyOfThings = (String) extras.get(KEY);

		/* obtaining my object that gets returned from my Json Data */
		Message message = Message.obtain();
		message.arg1 = Activity.RESULT_OK;
		message.obj = returnJsonData(keyOfThings);
		
		try {
			messenger.send(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Log.i("OnHandleIntent", e.getMessage().toString());
			e.printStackTrace();
		}
		
	}
	
	/* method used to get the JSON data */
	public String returnJsonData(String userInput){ 
		/* creation of url
		* calling out to my weather api 
		*/
		String completeURL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + userInput + "&mode=json&cnt=3";
		//String completeURL = "http://api.openweathermap.org/data/2.5/weather?q=" + userInput + "/us";
		
		
		URL finalURL = null;
		try {
			finalURL = new URL(completeURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			/* creating a temp string to hold the response */
			String response = "";
			response = WebInfo.getURLStringResponse(finalURL);

		return response;
	}
}
