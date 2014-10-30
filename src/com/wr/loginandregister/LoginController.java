package com.wr.loginandregister;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author elinjli
 *
 */
public class LoginController {
	
	private Context mContext;
	
	private LoginDBAdapter mDBAdapter;
	
	private ContentResolver mContentResolver;
	
	public enum Method{
		DBADAPTER, CONTENTPROVIDE
	}
	
	private Method mMethod;
		
	LoginController(Context theContext)
	{
		mContext = theContext;
		
		mMethod = Method.CONTENTPROVIDE;
		
		mContentResolver = mContext.getContentResolver();
		
		// create a instance of SQLite Database
		mDBAdapter = new LoginDBAdapter(mContext);
		mDBAdapter.open();
	}
	
	/**
	 * 
	 * @param theMethod
	 */
	void setMethod(Method theMethod)
	{
		mMethod = theMethod;
	}
	
	
	/**
	 * 
	 * @param theEmail
	 * @param thePassword
	 * @return
	 */
	boolean checkPassword(String theEmail, String thePassword)
	{
		
		String realPassword = this.getPassword(theEmail);
		
		// check if the Stored password matches with  Password entered by user
		if(thePassword.equals(realPassword))
		{
			return true;
		}

		return false;
	}
	
	/**
	 * 
	 * @param theEmail
	 * @return
	 */
	String getPassword(String theEmail)
	{
		String password = "";
		
		switch( mMethod )
		{
		case DBADAPTER:
			password = this.getPasswordByDBAdapter(theEmail);
			break;
			
		case CONTENTPROVIDE:
			password = this.getPasswordByCotentProvide(theEmail);
		}
		
		return password;
	}
	
	/**
	 * 
	 * @param theEmail
	 * @return
	 */
	String getPasswordByDBAdapter(String theEmail)
	{
		String password = "";
		
		password = mDBAdapter.getSinlgeEntry(theEmail);
		
		return password;
	}
	
	/**
	 * 
	 * @param theEmail
	 * @return
	 */
	String getPasswordByCotentProvide(String theEmail)
	{
		// Specify the result column projection. Return the minimum set
		// of columns required to satisfy your requirements.
		String[] result_columns = new String[] {
				LoginDBOpenHelper.KEY_PASSWORD};

		// Specify the where clause that will limit your results.
		String where = LoginDBOpenHelper.KEY_EMAIL + "= ?" ;

		// Replace these with valid SQL statements as necessary.
		String whereArgs[] = new String[] {theEmail};
		String order = null;
		// Return the specified rows.
		Cursor resultCursor = mContentResolver.query(LoginContentProvider.CONTENT_URI,
				result_columns, where, whereArgs, order);

		String storedPassword = "";

		if (resultCursor!=null)
		{
			// Find the index to the column(s) being used.
			int KEY_PASSWORD_INDEX = resultCursor.getColumnIndexOrThrow(
					LoginDBOpenHelper.KEY_PASSWORD);

			// Iterate over the cursors rows.
			// The Cursor is initialized at before first, so we can
			// check only if there is a ¡°next¡± row available. If the
			// result Cursor is empty, this will return false.
			while (resultCursor.moveToNext()) {
				storedPassword = resultCursor.getString(KEY_PASSWORD_INDEX);
			}
			// Close the Cursor when you¡¯ve finished with it.
			resultCursor.close();
		}
		
		return storedPassword;
	}
	
	/**
	 * 
	 * @param theFullName
	 * @param theEmail
	 * @param thePassword
	 * @return
	 */
	boolean register(String theFullName, String theEmail, String thePassword)
	{
		// Create a new row of values to insert.
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put(LoginDBOpenHelper.KEY_FULL_NAME,
				theFullName);
		newValues.put(LoginDBOpenHelper.KEY_EMAIL,
				theEmail);
		newValues.put(LoginDBOpenHelper.KEY_PASSWORD,
				thePassword);
		
		boolean result = false;
		
		switch( mMethod )
		{
		case DBADAPTER:
			result = this.registerByDBAdapter(newValues);
			break;

		case CONTENTPROVIDE:
			result = this.registerByCotentProvide(newValues);
		}
		
		
		
		return result;

	}

	/**
	 * 
	 * @param theValue
	 * @return
	 */
	boolean registerByDBAdapter(ContentValues theValue)
	{
		mDBAdapter.insertEntry(theValue);
		
		return false;
	}
	
	/**
	 * 
	 * @param theValue
	 * @return
	 */
	boolean registerByCotentProvide(ContentValues theValue)
	{
		// Insert the row into your table
		mContentResolver.insert(LoginContentProvider.CONTENT_URI,	theValue);
		
		return true;
	}
	
	/**
	 * 
	 */
	void onDestroy()
	{
		mDBAdapter.close();
	}
}
