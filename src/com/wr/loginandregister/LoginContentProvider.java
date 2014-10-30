package com.wr.loginandregister;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class LoginContentProvider extends ContentProvider {
	
	
	
	public static final String AUTHORITY = "com.wr.loginandregister.logincontentprovider";
	public static final String BASE_PATH = "login";
	
	public static final Uri CONTENT_URI =
			Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
	
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
		      + "/login";
		  public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
		      + "/login";
	
	// Create the constants used to differentiate between
	// the different URI requests.
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	private static final UriMatcher uriMatcher;
	// Populate the UriMatcher object, where a URI ending
	// in ¡®elements¡¯ will correspond to a request for all
	// items, and ¡®elements/[rowID]¡¯ represents a single row.
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, BASE_PATH, ALLROWS);
		uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", SINGLE_ROW);
	}

	//The index (key) column name for use in where clauses.
//	public static final String KEY_ID = ¡°_id¡±;
//	//The name and column index of each column in your database.
//	//These should be descriptive.
//	public static final String KEY_COLUMN_1_NAME = ¡°KEY_COLUMN_1_NAME¡±;
	
	//SQLite Open Helper variable
	private LoginDBOpenHelper loginOpenHelper;
	@Override
	public boolean onCreate() {
		//Construct the underlying database.
		//Defer opening the database until you need to perform
		//a query or transaction.
		loginOpenHelper = new LoginDBOpenHelper(getContext());
				
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		//Open the database.
		SQLiteDatabase db = loginOpenHelper.getWritableDatabase();
		//Replace these with valid SQL statements if necessary.
		String groupBy = null;
		String having = null;
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(LoginDBOpenHelper.DATABASE_TABLE);
		
		//If this is a row query, limit the result set to the
		//passed in row.
//		switch (uriMatcher.match(uri)) {
//		case SINGLE_ROW :
//			
////			String rowID = uri.getPathSegments().get(1);
////			queryBuilder.appendWhere(LoginDBOpenHelper.KEY_ID + "=" + rowID);
//		default: break;
//		}
		
//		if( !TextUtils.isEmpty(selection) )
//			queryBuilder.appendWhere(selection);
		
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, having, sortOrder);
		return cursor;
	}
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)

	{
		// Open a read / write database to support the transaction.
		SQLiteDatabase db = loginOpenHelper.getWritableDatabase();
		
		// If this is a row URI, limit the deletion to the specified row.
//		switch (uriMatcher.match(uri)) {
//		case SINGLE_ROW :
////			String rowID = uri.getPathSegments().get(1);
////			selection = LoginDBOpenHelper.KEY_ID + "=" + rowID
////					+ (!TextUtils.isEmpty(selection) ?
////							" AND (" + selection + ')' : "");
//		default: break;
//		}
		
		// To return the number of deleted items, you must specify a where
		// clause. To delete all rows and return a value, pass in ¡°1¡±.
		if (selection == null)
			selection = "1";
		
		// Execute the deletion.
		int deleteCount = db.delete(LoginDBOpenHelper.DATABASE_TABLE,
				selection, selectionArgs);
		
		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}
	
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// Open a read / write database to support the transaction.
		SQLiteDatabase db = loginOpenHelper.getWritableDatabase();
		
		// To add empty rows to your database by passing in an empty
		// Content Values object, you must use the null column hack
		// parameter to specify the name of the column that can be
		// set to null.
		String nullColumnHack = null;
		// Insert the values into the table
		long id = db.insert(LoginDBOpenHelper.DATABASE_TABLE,
				nullColumnHack, values);
		if (id > -1) {
			// Construct and return the URI of the newly inserted row.
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
			// Notify any observers of the change in the data set.
			getContext().getContentResolver().notifyChange(insertedId, null);

			return insertedId;
		}
		else
			return null;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// Open a read / write database to support the transaction.
		SQLiteDatabase db = loginOpenHelper.getWritableDatabase();
		
		// If this is a row URI, limit the deletion to the specified row.
//		switch (uriMatcher.match(uri)) {
//		case SINGLE_ROW :
////			String rowID = uri.getPathSegments().get(1);
////			selection = KEY_ID + ¡°=¡± + rowID
////					+ (!TextUtils.isEmpty(selection) ?
////							¡° AND (¡° + selection + ¡®)¡¯ : ¡°¡±);
//		default: break;
//		}
		
		// Perform the update.
		int updateCount = db.update(LoginDBOpenHelper.DATABASE_TABLE,
				values, selection, selectionArgs);
		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}
	
	@Override
	public String getType(Uri uri) {
		// Return a string that identifies the MIME type
		// for a Content Provider URI
		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			return CONTENT_TYPE;
		case SINGLE_ROW:
			return CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
	
}

