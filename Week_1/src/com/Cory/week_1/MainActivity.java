package com.Cory.week_1;

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

public class MainActivity extends Activity {

	// global variables
	Context _context;
	EditText userInputBox;
	ListView listView;
	
	FileManager m_file;
	String fileName = "json_info.txt";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // making sure to provide the context
        _context = this;
        
        // creating a singleton out of my WebInfo class
        WebInfo.getInstance();
		String connectionType = WebInfo.getConnectionType(_context);
        
        Log.i("Connection type", connectionType);
        
        // targetting my listView
        listView = (ListView)this.findViewById(R.id.list);
        View listHeader = this.getLayoutInflater().inflate(R.layout.list_header, null);
        listView.addHeaderView(listHeader);
        
        userInputBox = (EditText)findViewById(R.id.userInput);
        
        
        Button goButton = (Button)findViewById(R.id.goButton);
        goButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// converts the user input into a string.  This will need to check
				// for whitespace and also if the user has not inputted anything
				String userInputString = userInputBox.getText().toString();
				
				final Handler JsonHandler = new Handler(){

					@Override
					public void handleMessage(Message message){
						
						// what gets returned from the called service
						Object returnedObject = message.obj;
						
						// casting the object to a string
						String returnedObjectString = returnedObject.toString();
						
						if(message.arg1 == RESULT_OK && returnedObject != null){
							
							Log.i("information", returnedObjectString);
							
							// calls on my FileManager class
					        m_file = FileManager.getInstance();
					        m_file.writeStringFile(_context, fileName, returnedObjectString);
							
							//text.setText(m_file.readStringFile(_context, fileName));
							
							//Log.i("object", returnedObjectString);
					        
					        displayData();
							
						}
					}
		    		
		    	};
				
				
				
		    	// creation of my messenger to the service
		    	Messenger jsonMessenger = new Messenger(JsonHandler);
		
		    	Intent myServiceIntent = new Intent(_context, JSONWeatherService.class);
		
		    	// basically this passes info to my service
		    	myServiceIntent.putExtra("messenger", jsonMessenger);
		    	myServiceIntent.putExtra("key", userInputString);
		    	startService(myServiceIntent);
			}
        });
         
    }
    
    // this will parse out the saved file and present it back to the user
    public void displayData(){
    	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
