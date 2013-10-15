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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class FileManager {

	private static FileManager m_instance;
	
	/* constructor */
	private FileManager(){
		
	}
	
	public static FileManager getInstance(){
		if(m_instance == null){
			m_instance = new FileManager();
		}
		return m_instance;
	}
	
	public Boolean writeStringFile(Context context, String fileName, String content){
		Boolean result = false;
		
		
		FileOutputStream fos = null;
		
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			Log.i("Write String file", "Success");
			
		} catch (Exception e){
			Log.e("Write file error", e.toString());
		}
		return result;
	}
	
	public String readStringFile(Context context, String filename){
		String content = "";
		
		FileInputStream fis = null;
		
		try{
			
			fis = context.openFileInput(filename);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer contentBuffer = new StringBuffer();
			
			while ((bytesRead = bis.read(contentBytes)) != 1){
				content = new String(contentBytes, 0, bytesRead);
				contentBuffer.append(content);
			}
			
			content = contentBuffer.toString();
		} catch (Exception e) {
			
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				Log.e("Close file Error", e.toString());
				//e.printStackTrace();
			}
		}
		
		return content;
	}
	
}