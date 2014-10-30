package com.wr.loginandregister;

import com.wr.loginandregister.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    
	private EditText editTextFullName, editTextEmail, editTextPassword;
	private Button btnRegister;
	
	private LoginController mController;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// Switching to Login Screen/closing register screen
				finish();
			}
		});
        
        mController = new LoginController(this);
        
        // Get Refferences of Views
        editTextFullName=(EditText)findViewById(R.id.reg_fullname);
        editTextEmail = (EditText)findViewById(R.id.reg_email);
        editTextPassword=(EditText)findViewById(R.id.reg_password);
        
        btnRegister=(Button)findViewById(R.id.btnRegister);
        
        btnRegister.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View v) {

        		String fullName=editTextFullName.getText().toString();
        		String emailAddress = editTextEmail.getText().toString();
        		String password=editTextPassword.getText().toString();

        		// check if any of the fields are vaccant
        		if( TextUtils.isEmpty(fullName) 
        				|| TextUtils.isEmpty(password) 
        				|| TextUtils.isEmpty(emailAddress) )
        		{
        			Toast.makeText(getApplicationContext(), "FullName, Email or Password is Empty.", Toast.LENGTH_LONG).show();
        			return;
        		}

        		boolean result = mController.register(fullName, emailAddress, password);
        		
        		if( result )
        		{
        			Toast.makeText(getApplicationContext(), "Register Successfully.", Toast.LENGTH_LONG).show();
        		}
        		else
        		{
        			Toast.makeText(getApplicationContext(), "Register failed.", Toast.LENGTH_LONG).show();
        		}
        		
  
        	}
        });
	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		
		mController.onDestroy();
	}
}