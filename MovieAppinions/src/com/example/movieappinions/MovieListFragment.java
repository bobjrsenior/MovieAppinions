package com.example.movieappinions;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

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
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
	ParseQueryAdapter<ParseObject> adapter;
	ArrayAdapter<String> adapterTest;
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
		alertDialogBuilder.setTitle("Loading Movies...")
			.setIcon(R.drawable.app_logo)
			.setMessage("")
			.setCancelable((false));

	}

	@Override
	public void onStart() {
		super.onStart();
		//showDialog();
		ArrayList<String> contacts = new ArrayList<String>();
		adapterTest = new ArrayAdapter<String>(mListener.getContext(), android.R.layout.simple_list_item_1, contacts);
		
		ContentResolver cr = mListener.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
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
                     while (pCur.moveToNext()) {
                         String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                         contacts.add(name + " : " + phoneNo);
                         //Toast.makeText(mListener.getContext(), "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                     }
                    pCur.close();
                }
            }
        }
        textListView = (ListView) getView().findViewById(R.id.contacts_list);
        textListView.setAdapter(adapterTest);
        
        //alertDialog.dismiss();
	}

	/**
	 * Retrieves all contacts and how many movies they have reviewed
	 */
	private void retrieveContacts(){
		
	}
	
	/**
	 * Retrieved movies related to the search term
	 */
	private void retrieveMovies(){
		
	}
	
	public interface OnFragmentInteractionListener {
		public void selectedItem(ParseObject obj);
		public Context getContext();
		public ContentResolver getContentResolver();
	}



	
	public void showDialog(){
		alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}
	
	
}
