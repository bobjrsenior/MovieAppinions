package com.example.movieappinions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;






import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.ParseQueryAdapter.OnQueryLoadListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Used to show a list of movies returned by Rotten Tomatoes or a list of your friends
 *
 */
public class MovieListFragment extends Fragment {
	
	String type;
	String searchTerm;
	EditText itemText;
	ListView textListView;
	Intent i;
	FriendListViewAdapter adapter;
	ArrayAdapter<Movie> moviesAdapter;
	ArrayList<Movie> movies;
	ArrayList<Contact> contacts;
	AlertDialog alertDialog;
	AlertDialog.Builder alertDialogBuilder;
	 
	private OnFragmentInteractionListener mListener;

	public MovieListFragment(String type){
		super();
		this.type = type;
	}
	
	public MovieListFragment(String type, String searchTerm){
		super();
		this.type = type;
		this.searchTerm = searchTerm;
	}
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_movie_list, container, false);
		Log.d("demo", "onCreateView()");
		return view;
	}
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("demo", "onCreate()");			
		 
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("demo", "onActivityCreated");
		alertDialogBuilder = new AlertDialog.Builder(mListener.getContext());
		if(type.equals("Search")){
			alertDialogBuilder.setTitle("Loading Movies...")
				.setIcon(R.drawable.app_logo)
				.setMessage("")
				.setCancelable((false));
		}
		else if(type.equals("Friends")){
			alertDialogBuilder.setTitle("Loading Friends...")
			.setIcon(R.drawable.app_logo)
			.setMessage("")
			.setCancelable((false));
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		showDialog();
		Log.d("demo", "onstart");
		if(type.equals("Search")){
			retrieveMovies();
		}
		else if(type.equals("Friends")){
			retrieveContacts();
		}
        //alertDialog.dismiss();
	}

	/**
	 * Retrieves all contacts and how many movies they have reviewed
	 */
	private void retrieveContacts(){
		contacts = new ArrayList<Contact>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
		query.whereEqualTo("owner", ParseUser.getCurrentUser().getUsername());
		query.setLimit(1000);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> contactsList, ParseException e) {
		        if (e == null) {
		            Log.d("score", "Retrieved " + contactsList.size() + " contacts");
		            for(ParseObject obj : contactsList){
		            	Log.d("demo", "a" + (contactsList == null));
		            	Contact temp = new Contact();
		            	
		            	ArrayList<String> test = (ArrayList<String>) obj.get("name");
		            	temp.setName(test.get(0));
		            	temp.setNumber(obj.getString("phoneNum"));
			        	contacts.add(temp);
			        	Log.d("demo", "b");
			        }
			        adapter = new FriendListViewAdapter(mListener.getContext(), android.R.layout.simple_list_item_1, contacts, getFragmentManager());
			        ((ListView) getActivity().findViewById(R.id.contacts_list)).setAdapter(adapter);
			        
			        alertDialog.dismiss();
		            
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
			}
		});
	}
	
	/**
	 * Retrieved movies related to the search term
	 */
	private void retrieveMovies(){
		String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + APIKeys.ROTTEN_TOMATOES_API_KEY + "&q=" + searchTerm;
		Log.d("Demo", url);
		new GetMovies().execute(url);
	}
	
	public interface OnFragmentInteractionListener {
		public void selectedItem(ParseObject obj);
		public Context getContext();
		public ContentResolver getContentResolver();
		public void onBackPressed();
		public boolean isConnectedOnline();
	}



	
	public void showDialog(){
		alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}
	
	public class GetMovies extends AsyncTask<String, Void, ArrayList<Movie>>{

	
		public GetMovies() {

		}
		
		
		@Override
		protected ArrayList<Movie> doInBackground(String... params) {
			Log.d("demo", "start async");
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.connect();
				int statusCode = con.getResponseCode();
				if(statusCode == HttpURLConnection.HTTP_OK){
					BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                            sb.append(line);
                    }
                    return JSONUtils.MovieParser.parseMovies(sb.toString());
				}
				else{
					Log.d("demo", "" + statusCode);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Movie> result) {
			super.onPostExecute(result);
			Log.d("demo","tt");
			if(result != null){
				movies = result;
				moviesAdapter = new MovieListViewAdapter(mListener.getContext(), R.layout.movie_list_item, movies);
				((ListView) getActivity().findViewById(R.id.contacts_list)).setAdapter(moviesAdapter);
				Log.d("demo", "G:" + result.size());
				if(result.size() == 0){
					Toast.makeText(mListener.getContext(), "No Results Found", Toast.LENGTH_LONG).show();
					mListener.onBackPressed();
				}
			}
			else{
				Toast.makeText(mListener.getContext(), "No Results Found", Toast.LENGTH_LONG).show();
				mListener.onBackPressed();
			}
			
			alertDialog.dismiss();
		}	
	}
}
