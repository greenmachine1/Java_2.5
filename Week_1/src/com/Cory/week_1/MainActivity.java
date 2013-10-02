package com.Cory.week_1;

import com.Cory.lib.WebInfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Context _context;
	
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
        
        Button goButton = (Button)findViewById(R.id.goButton);
        goButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent myIntent = new Intent(v.getContext(), JSONWeatherService.class);
				
				myIntent.putExtra("key", "Yes");
			}
        	
        });
        
        final Handler serviceHandler = new Handler(){
        	public void handleMessage(Message message){
        		
        		Object returnedObject = message.obj;
        		
        		if(message.arg1 == RESULT_OK && returnedObject != null){
        			
        			// do stuff here
        		}
        	}
        };
        
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
