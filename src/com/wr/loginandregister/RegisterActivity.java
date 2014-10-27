package com.wr.loginandregister;

import com.wr.loginandregister.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    
	EditText editTextFullName, editTextEmail, editTextPassword;
	Button btnRegister;
	
	LoginDataBaseAdapter loginDataBaseAdapter;
	
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
        
        // get Instance  of Database Adapter   
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextFullName=(EditText)findViewById(R.id.reg_fullname);
        editTextEmail = (EditText)findViewById(R.id.reg_email);
        editTextPassword=(EditText)findViewById(R.id.reg_password);
        
        btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		String fullName=editTextFullName.getText().toString();
        		String emailAddress = editTextEmail.getText().toString();
        		String password=editTextPassword.getText().toString();
        		
        		// check if any of the fields are vaccant
        		if(fullName.equals("") || password.equals("") || emailAddress.equals(""))
        		{
        			Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
        			return;
        		}
        		
//        		// check if both password matches
//        		if(!password.equals(confirmPassword))
//        		{
//        			Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
//        			return;
//        		}
//        		else
        		{
        			// Save the Data in Database
        			loginDataBaseAdapter.insertEntry(fullName, emailAddress, password);
        			Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
        		}
        	}
        });
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		loginDataBaseAdapter.close();
	}
}