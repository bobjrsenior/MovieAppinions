package com.example.movieappinions;

import com.parse.ParseUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class HomeActivity extends Activity {

	ImageView myReviews, favorites, search, friends, logout, sync;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		myReviews = (ImageView) findViewById(R.id.imageView1);
		favorites = (ImageView) findViewById(R.id.imageView2);
		search = (ImageView) findViewById(R.id.imageView3);
		friends = (ImageView) findViewById(R.id.imageView4);
		sync = (ImageView) findViewById(R.id.imageView5);
		logout = (ImageView) findViewById(R.id.imageView6);


		myReviews.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				startActivity(i);
				
			}
		});
		favorites.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				startActivity(i);				
			}
		});
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				startActivity(i);				
			}
		});
		friends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				startActivity(i);				
			}
		});
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				finish();				
			}
		});
		sync.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProgressDialog dialog = ProgressDialog.show(HomeActivity.this,"", "Syncing Contacts, Please wait...", true);
				//Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				//startActivity(i);
			    dialog.dismiss();
			}
		});
		
	}
}
