package com.example.movieappinions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.movieappinions.MovieListFragment.OnFragmentInteractionListener;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link MovieSearchFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class MovieSearchFragment extends Fragment {

	private EditText searchTerm;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment MovieSearchFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static MovieSearchFragment newInstance(String param1, String param2) {
		MovieSearchFragment fragment = new MovieSearchFragment();
		return fragment;
	}

	public MovieSearchFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_movie_search, container,
				false);
	}

	@Override
	public void onStart() {
		super.onStart();
		
		searchTerm = (EditText) getView().findViewById(R.id.searchTerm);
		((Button) getView().findViewById(R.id.searchButton)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!mListener.isConnectedOnline()){
					Toast.makeText(mListener.getContext(), "Not Connected to Internet", Toast.LENGTH_LONG).show();
				}
				else{
					String search = searchTerm.getText().toString().trim();
					if(search.equals("")){
						search = "Toy+Story";
					}
					else{
						try {
							search = URLEncoder.encode(search, "UTF-8");
						} catch (UnsupportedEncodingException e) {
	
						}
					}
					InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			        mgr.hideSoftInputFromWindow(searchTerm.getWindowToken(), 0);
					getFragmentManager().beginTransaction()
					.replace(R.id.LinearLayout1, new MovieListFragment("Search", search), "movie_list")
					.addToBackStack("movie_list")
					.commit();
				}
			}
		});
	}

}
