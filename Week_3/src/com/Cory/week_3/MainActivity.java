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

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Cory.lib.WebInfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	// global variables
	Context _context;
	EditText userInputBox;
	TextView text;
    ListView listView;
	
	FileManager m_file;
	String fileName = "json_info.txt";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* making sure to provide the context */
        _context = this;
        
        text = (TextView)findViewById(R.id.resultText);
        
 
        /* creating a singleton out of my WebInfo class */
        WebInfo.getInstance();
		String connectionType = WebInfo.getConnectionType(_context);
		
        
        Log.i("Connection type", connectionType);
        
        /* targetting my listView */
        listView = (ListView)this.findViewById(R.id.list);
        View listHeader = this.getLayoutInflater().inflate(R.layout.list_header, null);
        listView.addHeaderView(listHeader);
        
        userInputBox = (EditText)findViewById(R.id.userInput);
       
        Button goButton = (Button)findViewById(R.id.goButton);
        goButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				/* converts the user input into a string.  This will need to check
				* for whitespace and also if the user has not inputted anything */
				String userInputString = userInputBox.getText().toString();
				
				final Handler JsonHandler = new Handler(){

					@Override
					public void handleMessage(Message message){
						
						/* what gets returned from the called service */
						Object returnedObject = message.obj;
						
						/* casting the object to a string */
						String returnedObjectString = returnedObject.toString();
						
						if(message.arg1 == RESULT_OK && returnedObject != null){
							
							/* calls on my FileManager class */
					        m_file = FileManager.getInstance();
					        m_file.writeStringFile(_context, fileName, returnedObjectString);

					        displayData();
							
						}
						
					}
		    		
		    	};
				
				
				
		    	/* creation of my messenger to the service */
		    	Messenger jsonMessenger = new Messenger(JsonHandler);
		
		    	Intent myServiceIntent = new Intent(_context, JSONWeatherService.class);
		
		    	/* basically this passes info to my service */
		    	myServiceIntent.putExtra("messenger", jsonMessenger);
		    	myServiceIntent.putExtra("key", userInputString);
		    	startService(myServiceIntent);
			}
        });
         
    }
    
    /* this will parse out the saved file and present it back to the user */
    public void displayData(){
    	
    	/* loading my file into a string */
    	String JSONString = m_file.readStringFile(this, fileName);
    	//Log.i("response", JSONString);
    	
    	ArrayList<HashMap<String, String>>mylist = new ArrayList<HashMap<String,String>>();
    	JSONObject job = null;
    	JSONArray results = null;
    	
    		/* getting the array from the field "results" */
    		try {
				job = new JSONObject(JSONString);
				results = job.getJSONArray("weather");
				
				/* setting up the different strings to look for in the json object */
				String weatherString = results.getJSONObject(0).getString("main");
				
				String nameString = job.getString("name");

				String windSpeed = job.getJSONObject("wind").getString("speed");
				
	    		HashMap<String, String> displayMap = new HashMap<String, String>();
	    		displayMap.put("name", nameString);
	    		displayMap.put("weather", weatherString);
	    		displayMap.put("speed", windSpeed);
	    		
	    		mylist.add(displayMap);
	    		
	    		/* this is complicated but it basically assigns the rows for each element */
	    		SimpleAdapter adapter = new SimpleAdapter(this, mylist, R.layout.list_row, 
	    				new String[] {"name", "weather", "speed"}, 
	    				new int[] {R.id.name, R.id.weather, R.id.speed});
	    		
	    		
	    		listView.setAdapter(adapter);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
