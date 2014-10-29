package com.wr.loginandregister;

import com.wr.loginandregister.R;

import android.content.ContentResolver;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
//	LoginDBAdapter loginDataBaseAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
			}
		});
        
        // create a instance of SQLite Database
//	     loginDataBaseAdapter=new LoginDBAdapter(this);
//	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     

    }
    
    
    // Methos to handleClick Event of login Button
 	public void signIn(View V)
 	   {	
 		    // get the Refferences of views
 		    final  EditText editTextEmail=(EditText)findViewById(R.id.sig_email);
 		    final  EditText editTextPassword=(EditText)findViewById(R.id.sig_password);
 		    

 		    // get The User name and Password
 		    String emailAddress=editTextEmail.getText().toString();
 		    String password=editTextPassword.getText().toString();

 		    // fetch the Password form database for respective user name
// 		    String storedPassword=loginDataBaseAdapter.getSinlgeEntry(emailAddress);
 		    
 		    // Get the Content Resolver.
 		   ContentResolver cr = getContentResolver();
 		   
 		   // Specify the result column projection. Return the minimum set
 		   // of columns required to satisfy your requirements.
 		   String[] result_columns = new String[] {
 				  LoginDBOpenHelper.KEY_PASSWORD};
 		   
 		   // Specify the where clause that will limit your results.
 		   String where = LoginDBOpenHelper.KEY_EMAIL + "= ?" ;
 		   
 		   // Replace these with valid SQL statements as necessary.
 		   String whereArgs[] = new String[] {emailAddress};
 		   String order = null;
 		   // Return the specified rows.
 		   Cursor resultCursor = cr.query(LoginContentProvider.CONTENT_URI,
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

 		    // check if the Stored password matches with  Password entered by user
 		    if(password.equals(storedPassword))
 		    {
 		    	Toast.makeText(this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();

 		    }
 		    else
 		    {
 		    	Toast.makeText(this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
 		    }

 	}

 	@Override
 	protected void onDestroy() {
 		super.onDestroy();
 	    // Close The Database
// 		loginDataBaseAdapter.close();
 	}
}