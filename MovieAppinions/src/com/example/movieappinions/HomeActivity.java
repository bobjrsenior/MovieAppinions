package com.example.movieappinions;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.internal.AsyncCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HomeActivity extends Activity implements MovieListFragment.OnFragmentInteractionListener{

	ImageView myReviews, favorites, search, friends, logout, sync;
	LinearLayout layout1, layout2, layout3, layout4;
	ProgressDialog dialog;	
	
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
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		layout3 = (LinearLayout) findViewById(R.id.layout3);
		layout4 = (LinearLayout) findViewById(R.id.layout4);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Syncing Contacts, Please wait...");
		dialog.setIndeterminate(false);
		dialog.setMax(100);
		dialog.setProgress(0);
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		myReviews.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				//startActivity(i);
				if(!isConnectedOnline()){
					Toast.makeText(HomeActivity.this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
				}
				else{
					
				}
			}
		});
		favorites.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				//startActivity(i);			
				if(!isConnectedOnline()){
					Toast.makeText(HomeActivity.this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
				}
				else{
					
				}
			}
		});
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				//startActivity(i);			
				getFragmentManager().beginTransaction()
				.add(R.id.LinearLayout1, new MovieSearchFragment(), "movie_search")
				.addToBackStack("movie_list")
				.commit();
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.GONE);
				layout3.setVisibility(View.GONE);
				layout4.setVisibility(View.GONE);
			}
		});
		friends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isConnectedOnline()){
					Toast.makeText(HomeActivity.this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
				}
				else{
					getFragmentManager().beginTransaction()
					.add(R.id.LinearLayout1, new MovieListFragment("Friends"), "contacts_list")
					.addToBackStack("contacts_list")
					.commit();
					layout1.setVisibility(View.GONE);
					layout2.setVisibility(View.GONE);
					layout3.setVisibility(View.GONE);
					layout4.setVisibility(View.GONE);	
				}
			}
		});
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeActivity.this, MainActivity.class);
				ParseUser.logOut();
				finish();
				startActivity(i);				
			}
		});
		sync.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				//startActivity(i);
				if(!isConnectedOnline()){
					Toast.makeText(HomeActivity.this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
				}
				else{
					dialog.show();
					new MergeContacts().execute();
				}
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		if(getFragmentManager().getBackStackEntryCount() > 0){
			getFragmentManager().popBackStackImmediate();
			if(getFragmentManager().getBackStackEntryCount() == 0){
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.VISIBLE);
				layout3.setVisibility(View.VISIBLE);
				layout4.setVisibility(View.VISIBLE);
			}
		}
		else{
			finish();
			super.onBackPressed();
		}
	}

	public class MergeContacts extends AsyncTask<Void, Integer, Void>{
		ArrayList<ParseObject> users;
		@Override
		protected synchronized Void doInBackground(Void... params) {
			
			//Remove old contacts
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
			query.whereEqualTo("owner", ParseUser.getCurrentUser().getUsername());
			query.setLimit(1000);
			ArrayList<ParseObject> contactsList = null;
			try {
				contactsList = (ArrayList<ParseObject>) query.find();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	for(int a = 0; a < contactsList.size(); a ++){
	        	try {
					contactsList.get(a).delete();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
	        	publishProgress((int) (10f * (a / contactsList.size())));
	        }
			
			query = ParseQuery.getQuery("_User");
			query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
			query.setLimit(1000);
			try {
				users = (ArrayList<ParseObject>) query.find();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			publishProgress(20);
			syncContacts();

			dialog.dismiss();
			return null;
		}
		
		protected void syncContacts(){
			Log.d("demo", "GG");
			ContentResolver cr = getContentResolver();
	        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
	                null, null, null, null);
	        float count = cur.getCount();
	        float current = 1;
	        if (cur.getCount() > 0) {
	            while (cur.moveToNext()) {
	                  String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
	                  String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	                  if (Integer.parseInt(cur.getString(
	                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
	                     Cursor pCur = cr.query(
	                               ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
	                               null,
	                               ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
	                               new String[]{id}, null);
	                     if (pCur.moveToNext()) {
	                         String phoneNum = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	                         Log.d("phoneNum", phoneNum);
	                         if(phoneNum.length() > 10){
	                        	 phoneNum = phoneNum.substring(phoneNum.length() - 10);
	                         }
	                         ParseObject contact = new ParseObject("Contacts");
	                         Log.d("demo", ":" + (contact == null));
	                         contact.add("owner", ParseUser.getCurrentUser().getUsername());
	                         contact.add("name", name);
	                         contact.add("phoneNum", phoneNum);
	                         for(ParseObject obj : users){
	             				Log.d("demo","G:" +  obj.getString("username") + ":" + phoneNum);
	             				if(obj.getString("username").equals(phoneNum)){
	             					contact.saveInBackground();
	             				}
	             			}
	                     }
	                     pCur.close();
	                }
                     publishProgress(20 + (int)(90f * current / count));

                     current ++;
	            }
	        }
			cur.close();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			publishProgress(0);
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			dialog.setProgress(values[0]);
		}
		
	}
	
	public boolean isConnectedOnline(){
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()){
			return true;
		}
		return false;
	}

	@Override
	public void selectedItem(ParseObject obj) {
	
	}

	@Override
	public Context getContext() {
		return this;
	}
	
	
}
