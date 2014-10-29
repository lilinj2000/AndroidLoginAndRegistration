package com.wr.loginandregister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LoginDBOpenHelper extends SQLiteOpenHelper
{
	//	table column name
	public static final String KEY_ID = "_id";

	public static final String KEY_FULL_NAME = "FULLNAME";
	public static final String KEY_EMAIL = "EMAIL";
	public static final String KEY_PASSWORD = "PASSWORD";

	public static final String DATABASE_NAME = "login.db";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_TABLE = "LOGIN";
	
	// SQL Statement to create a new database.
	private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE +
			"( " + KEY_ID +" integer primary key autoincrement,"
			+ KEY_FULL_NAME + " text, " 
			+ KEY_EMAIL +" text, "
			+ KEY_PASSWORD + " text); ";
		
	public LoginDBOpenHelper(Context context) 
    {
	           super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Called when no database exists in disk and the helper class needs
	// to create a new one.
	@Override
	public void onCreate(SQLiteDatabase _db) 
	{
			_db.execSQL(DATABASE_CREATE);
			
	}
	// Called when there is a database version mismatch meaning that the version
	// of the database on disk needs to be upgraded to the current version.
	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) 
	{
			// Log the version upgrade.
			Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");
	
	
			// Upgrade the existing database to conform to the new version. Multiple
			// previous versions can be handled by comparing _oldVersion and _newVersion
			// values.
			// The simplest case is to drop the old table and create a new one.
			_db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
			// Create a new one.
			onCreate(_db);
	}
	

}

