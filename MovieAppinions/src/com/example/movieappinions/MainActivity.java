package com.example.movieappinions;


import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText phoneNumber, password;
	Button login, create;
	ParseUser currentUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Parse.initialize(this, APIKeys.PARSE_APPLICATION_KEY, APIKeys.PARSE_CLIENT_KEY);
        //getActionBar().setTitle("Login");
        //ParseUser.logOut();
        currentUser = ParseUser.getCurrentUser();
        if(currentUser != null){
        	Intent i = new Intent(getBaseContext(), MainActivity.class);
			startActivity(i);        	
        }
        phoneNumber = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.button1);
        create = (Button) findViewById(R.id.button2);
        
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentUser != null){
					Toast.makeText(MainActivity.this, "Already Logged in to: " + currentUser.getUsername(), Toast.LENGTH_LONG).show();
				}
				else if(phoneNumber.getText().length() == 0){
					Toast.makeText(MainActivity.this, "Insert Phone Number", Toast.LENGTH_LONG).show();
				}
				else if(password.getText().length() == 0){
					Toast.makeText(MainActivity.this, "Insert Password", Toast.LENGTH_LONG).show();
				}
				else{
					ParseUser.logInInBackground(phoneNumber.getText().toString(), password.getText().toString(), new LogInCallback() {
						
						@Override
						public void done(ParseUser user, ParseException e) {
							if(e == null){
								Log.d("demo", "good?");

								Toast.makeText(MainActivity.this, "Succesfully Logged In", Toast.LENGTH_LONG).show();
								currentUser = ParseUser.getCurrentUser();
								Intent i = new Intent(getBaseContext(),HomeActivity.class);
								startActivity(i);
							}
							else{
								Log.d("demo", "error");
								Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_LONG).show();
							}							
						}
					});
				}
			}
		});
        
        create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), SignUp.class);
				startActivity(i);
			}
		});
        
    }
	@Override
	protected void onResume() {
		currentUser = ParseUser.getCurrentUser();
		if(ParseUser.getCurrentUser() != null){
			Intent i = new Intent(getBaseContext(), HomeActivity.class);
			startActivity(i);
		}
		super.onResume();
	}
    
    
		
		
}

