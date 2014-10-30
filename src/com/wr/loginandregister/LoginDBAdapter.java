package com.wr.loginandregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDBAdapter 
{	
	// Variable to hold the database instance
	public  SQLiteDatabase db;
	
	// Context of the application using the database.
	private final Context context;
	
	// Database open/upgrade helper
	private LoginDBOpenHelper dbHelper;
	
	public  LoginDBAdapter(Context _context) 
	{
		context = _context;
		dbHelper = new LoginDBOpenHelper(context);
	}
	
	public  LoginDBAdapter open() throws SQLException 
	{
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() 
	{
		db.close();
	}

	public  SQLiteDatabase getDatabaseInstance()
	{
		return db;
	}

	public void insertEntry(ContentValues theValue)
	{
		// Insert the row into your table
		db.insert(LoginDBOpenHelper.DATABASE_TABLE, null, theValue);
	}

	public int deleteEntry(String emailAddress)
	{
		//String id=String.valueOf(ID);
		String where = LoginDBOpenHelper.KEY_EMAIL + "=?";
		int numberOFEntriesDeleted= db.delete(LoginDBOpenHelper.DATABASE_TABLE, where, new String[]{emailAddress}) ;
		
		return numberOFEntriesDeleted;
	}

	public String getSinlgeEntry(String emailAddress)
	{
		String password = "";
		
		Cursor cursor=db.query(LoginDBOpenHelper.DATABASE_TABLE, null,  LoginDBOpenHelper.KEY_EMAIL + "=?", new String[]{emailAddress}, null, null, null);
		
		if(cursor.getCount()<1)
		{
			cursor.close();
			return password;
		}
		
		cursor.moveToFirst();
		
		password = cursor.getString(cursor.getColumnIndex(LoginDBOpenHelper.KEY_PASSWORD));
		
		cursor.close();
		
		return password;				
	}
	
	public void  updateEntry(String fullName, String emailAddress, String password)
	{
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put(LoginDBOpenHelper.KEY_PASSWORD, fullName);
		updatedValues.put(LoginDBOpenHelper.KEY_PASSWORD,password);

		String where= LoginDBOpenHelper.KEY_EMAIL + " = ?";
		
		db.update(LoginDBOpenHelper.DATABASE_TABLE, updatedValues, where, new String[]{emailAddress});			   
	}		
}


