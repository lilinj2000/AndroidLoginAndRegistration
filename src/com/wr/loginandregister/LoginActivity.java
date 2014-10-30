package com.wr.loginandregister;

import com.wr.loginandregister.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private TextView registerScreen;
	private EditText editTextEmail;
	private EditText editTextPassword;
	
	private LoginController mController;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        // get thre reference for the UIs
        editTextEmail=(EditText)findViewById(R.id.sig_email);
        editTextPassword=(EditText)findViewById(R.id.sig_password);
		    
        registerScreen = (TextView) findViewById(R.id.link_to_register);
        
        mController = new LoginController( this );
        
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
			}
		});
        
    }
    
    
    // Methos to handleClick Event of login Button
 	public void signIn(View V)
 	   {	
 		    // get The User name and Password
 		    String emailAddress=editTextEmail.getText().toString();
 		    String password=editTextPassword.getText().toString();
 		    
 		    if ( TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password) )
 		    {
 		    	Toast.makeText(this, "Email or Password can not be empty.", Toast.LENGTH_LONG).show();
 		    	return ;
 		    }
 		    
 		    boolean result = mController.checkPassword(emailAddress, password);
 		    
 		    if( result )
 		    {
 		    	Toast.makeText(this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
 		    }
 		    else
 		    {
 		    	Toast.makeText(this, "Email and Password does not match", Toast.LENGTH_LONG).show();
 		    }
 		    
 		    return ;
 		    	    

 	}

 	@Override
 	protected void onDestroy() {
 		super.onDestroy();
 	   
 		mController.onDestroy();
 	}
}