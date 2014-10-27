package com.wr.loginandregister;

import com.wr.loginandregister.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	LoginDataBaseAdapter loginDataBaseAdapter;
	
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
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     

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
 		    String storedPassword=loginDataBaseAdapter.getSinlgeEntry(emailAddress);

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
 		loginDataBaseAdapter.close();
 	}
}