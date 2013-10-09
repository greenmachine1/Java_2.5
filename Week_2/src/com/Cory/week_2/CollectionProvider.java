package com.Cory.week_2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class CollectionProvider extends ContentProvider{

	public static final String AUTHORITY = "com.Cory.week_2.CollectionProvider";
	
	public static class weatherData implements BaseColumns {
		
		
		/*  I believe i will have to change the /items to something witin my json string*/
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.Cory.week_2.item";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.Cory.week_2.item";
	
		/* Define Columns */
		public static final String NAME_COLUMN = "Name";
		public static final String WEATHER_COLUMN = "Weather";
		public static final String SPEED_COLUMN = "Speed";
		
		public static final String[] PROJECTION = {"_Id", NAME_COLUMN, WEATHER_COLUMN, SPEED_COLUMN};
		
		/* constructor */ 
		private weatherData() {};
	
	}
	
	public static final int ITEMS = 1;
	public static final int ITEMS_ID  = 2;
	
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS);
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS_ID);
	}
	
	
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri){
		// TODO Auto-generated method stub
		
		switch (uriMatcher.match(uri)){
		case ITEMS:
			return weatherData.CONTENT_TYPE;
		
		case ITEMS_ID:
			return weatherData.CONTENT_ITEM_TYPE;
		
		}
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		
		MatrixCursor result = new MatrixCursor(weatherData.PROJECTION);
		
		String JSONString = FileManager.getInstance().readStringFile(getContext(), MainActivity.FILE_NAME);
		
		switch (uriMatcher.match(uri)){
		case ITEMS:
			return weatherData.CONTENT_TYPE;
		
		case ITEMS_ID:
			return weatherData.CONTENT_ITEM_TYPE;
		
		}
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
