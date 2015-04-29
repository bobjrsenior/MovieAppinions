package com.example.movieappinions;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {

	EditText firstName, lastName, phoneNumber, password, confirm;
	ParseUser currentUser;
	Button create, cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		currentUser = ParseUser.getCurrentUser();
		
		password = (EditText) findViewById(R.id.editText4);
		confirm = (EditText) findViewById(R.id.editText5);
		firstName = (EditText) findViewById(R.id.editText1);
		lastName = (EditText) findViewById(R.id.editText2);
		phoneNumber =(EditText) findViewById(R.id.editText3);
		cancel = (Button) findViewById(R.id.button2);
		create = (Button) findViewById(R.id.searchButton);
		
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser user = new ParseUser();
				if(currentUser != null){
					
					Toast.makeText(SignUp.this, "Already Logged in to: " + currentUser.getUsername(), Toast.LENGTH_LONG).show();
				}
				else if(firstName.getText().length() == 0){
					Toast.makeText(SignUp.this, "Insert First Name", Toast.LENGTH_LONG).show();
				}
				else if(lastName.getText().length() == 0){
					Toast.makeText(SignUp.this, "Insert Last Name", Toast.LENGTH_LONG).show();
				}
				else if(phoneNumber.getText().length() == 0){
					Toast.makeText(SignUp.this, "Insert Phone Number", Toast.LENGTH_LONG).show();
				}
				else if(password.getText().length() == 0){
					Toast.makeText(SignUp.this, "Insert Password", Toast.LENGTH_LONG).show();
				}
				else if(confirm.getText().length() == 0){
					Toast.makeText(SignUp.this, "Insert Password Confirmation", Toast.LENGTH_LONG).show();
				}
				else if(!password.getText().toString().equals(confirm.getText().toString())){
					Toast.makeText(SignUp.this, "Passwords Do Not Match", Toast.LENGTH_LONG).show();
				}
				else{
					user.setUsername(phoneNumber.getText().toString());
					user.setPassword(password.getText().toString());
					user.put("Name", firstName.getText().toString() + " " + lastName.getText().toString());
					user.signUpInBackground(new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
							if(e == null){
								Log.d("demo", "good?");
								Toast.makeText(SignUp.this, "Succesfully signed up", Toast.LENGTH_LONG).show();
								currentUser = ParseUser.getCurrentUser();
								finish();
							}
							else{
								Log.d("demo", "error");
								if(e.getCode() ==  ParseException.USERNAME_TAKEN){
									Toast.makeText(SignUp.this, "Email Exists", Toast.LENGTH_LONG).show();
								}
							}
						}
					});
				}				
			}
		});
		
		
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
