package com.wr.loginandregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter 
{
	//	table column name
	public static final String KYE_ID = "_id";

	public static final String KEY_FULL_NAME = "FULLNAME";
	public static final String KEY_EMAIL = "EMAIL";
	public static final String KEY_PASSWORD = "PASSWORD";

	private static final String DATABASE_NAME = "login.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "LOGIN";
	
	// SQL Statement to create a new database.
	static final String DATABASE_CREATE = "create table " + DATABASE_TABLE +
			"( " + KYE_ID +" integer primary key autoincrement,"
			+ KEY_FULL_NAME + " text, " 
			+ KEY_EMAIL +" text, "
			+ KEY_PASSWORD + " text); ";
	
	// Variable to hold the database instance
	public  SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private DataBaseHelper dbHelper;
	public  LoginDataBaseAdapter(Context _context) 
	{
		context = _context;
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public  LoginDataBaseAdapter open() throws SQLException 
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

		public void insertEntry(String fullName, String emailAddress, String password)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put(KEY_FULL_NAME, fullName);
			newValues.put(KEY_EMAIL, emailAddress);
			newValues.put(KEY_PASSWORD,password);
			
			// Insert the row into your table
			db.insert(DATABASE_TABLE, null, newValues);
			///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
		}
		
		public int deleteEntry(String emailAddress)
		{
			//String id=String.valueOf(ID);
		    String where = KEY_EMAIL + "=?";
		    int numberOFEntriesDeleted= db.delete(DATABASE_TABLE, where, new String[]{emailAddress}) ;
	       // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
	        return numberOFEntriesDeleted;
		}
		
		public String getSinlgeEntry(String emailAddress)
		{
			Cursor cursor=db.query(DATABASE_TABLE, null,  KEY_EMAIL + "=?", new String[]{emailAddress}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String password= cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
			cursor.close();
			return password;				
		}
		public void  updateEntry(String fullName, String emailAddress, String password)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put(KEY_PASSWORD, fullName);
			updatedValues.put(KEY_PASSWORD,password);
			
	        String where= KEY_EMAIL + " = ?";
		    db.update(DATABASE_TABLE, updatedValues, where, new String[]{emailAddress});			   
		}		
}


