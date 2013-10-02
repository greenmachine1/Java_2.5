package com.Cory.week_1;

import com.Cory.lib.WebInfo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	Context _context;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // making sure to provide the context
        _context = this;
        
        WebInfo.getInstance();
		String connectionType = WebInfo.getConnectionType(_context);
        
        Log.i("Connection type", connectionType);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
