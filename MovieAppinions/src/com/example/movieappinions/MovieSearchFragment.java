package com.example.movieappinions;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link MovieSearchFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class MovieSearchFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	private EditText searchTerm;

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

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
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public MovieSearchFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
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
				InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		        mgr.hideSoftInputFromWindow(searchTerm.getWindowToken(), 0);
				getFragmentManager().beginTransaction()
				.replace(R.id.LinearLayout1, new MovieListFragment("Search", searchTerm.getText().toString()), "movie_list")
				.addToBackStack("movie_list")
				.commit();
			}
		});
	}
	
	

}
