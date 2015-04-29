package com.example.movieappinions;

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
				Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				startActivity(i);
				//
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
				//Intent i = new Intent(HomeActivity.this, HomeActivity.class);
				//startActivity(i);
				dialog.show();
				new MergeContacts().execute();
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
			Intent i = new Intent(this, MainActivity.class);
			ParseUser.logOut();
			startActivity(i);
			super.onBackPressed();
		}
	}

	public class MergeContacts extends AsyncTask<Void, Integer, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			//Remove old contacts
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
			query.whereEqualTo("owner", ParseUser.getCurrentUser().getUsername());
			query.setLimit(1000);
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> contactsList, ParseException e) {
			        if (e == null) {
			            Log.d("score", "Retrieved " + contactsList.size() + " contacts");
			            
			        } else {
			            Log.d("score", "Error: " + e.getMessage());
			        }
			        for(int a = 0; a < contactsList.size(); a ++){
			        	try {
							contactsList.get(a).delete();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
			        	publishProgress((int) (10f * (a / contactsList.size())));
			        }
					
				}
			});
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
	                         ParseObject contact = new ParseObject("Contacts");
	                         contact.add("owner", ParseUser.getCurrentUser().getUsername());
	                         contact.add("name", name);
	                         contact.add("phoneNum", phoneNum);
	                         contact.saveInBackground();
	                     }
	                     pCur.close();
	                }
                     publishProgress(10 + (int)(90f * current / count));

                     current ++;
	            }
	        }
			cur.close();
			
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			publishProgress(0);
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			dialog.setProgress(values[0]);
		}
		
	}

	@Override
	public void selectedItem(ParseObject obj) {
	
	}

	@Override
	public Context getContext() {
		return this;
	}
}
