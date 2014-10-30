package com.wr.loginandregister.test;


import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.wr.loginandregister.LoginActivity;
import com.wr.loginandregister.R;

public class LoginActivityTest extends
android.test.ActivityUnitTestCase<LoginActivity> {

	private LoginActivity activity;
	
	private EditText editTextEmail;
	private EditText editTextPass;
	
	private TextView textViewLinkToReg;

	public LoginActivityTest() {
		super(LoginActivity.class);
	}

	@Override
	protected  void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), LoginActivity.class);
		
		startActivity(intent, null, null);
		
		activity = getActivity();
		
		editTextEmail = (EditText) activity.findViewById(R.id.sig_email);
		editTextPass = (EditText) activity.findViewById(R.id.sig_password);
		
		textViewLinkToReg = (TextView) activity.findViewById(R.id.link_to_register);
	}

	public void testPreConditions() {
		
		assertTrue(activity != null);
		
		assertTrue(editTextEmail != null);
		assertTrue(editTextPass != null);
		
		assertTrue(textViewLinkToReg != null);
		
	} 
	
	@Override
	protected void tearDown()  {
		activity.finish();
		
		try {
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	  
//	public  void testLayout() {
//		buttonId = com.vogella.android.test.simpleactivity.R.id.button1;
//		assertNotNull(activity.findViewById(buttonId));
//		Button view = (Button) activity.findViewById(buttonId);
//		assertEquals("Incorrect label of the button", "Start", view.getText());
//	}

	public  void testIntentTriggerViaOnClick() {

		textViewLinkToReg.performClick();
		
		Intent triggerIntent = getStartedActivityIntent();
		
		assertNotNull("Intent was null", triggerIntent);
		
		
	}

} 

