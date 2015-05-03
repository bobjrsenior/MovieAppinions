package com.example.movieappinions;

import java.util.ArrayList;
import java.util.List;

import com.example.movieappinions.MovieListFragment.OnFragmentInteractionListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link FriendReviewFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link FriendReviewFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class FriendReviewFragment extends Fragment {

	private OnFragmentInteractionListener mListener;
	private String friendName;
	private String friendNumber;
	private TextView friendText;
	private ArrayList<Movie> movies;
	private ArrayList<Integer> movieIds;
	ArrayAdapter<Movie> moviesAdapter;

	public FriendReviewFragment(String friendName, String friendNumber) {
		super();
		this.friendName = friendName;
		this.friendNumber = friendNumber;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_friend_review, container,
				false);
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
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onStart() {
		super.onStart();
		
		friendText = (TextView) getView().findViewById(R.id.friendName);
		friendText.setText(friendName);
		
		movies = new ArrayList<Movie>();
		movieIds = new ArrayList<Integer>();
		
		
		/*ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("SharedReviews");
		innerQuery.whereEqualTo("sharedTo", ParseUser.getCurrentUser().getUsername());
		innerQuery.setLimit(1000);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
		query.whereMatchesQuery("SharedReviews.movieId", innerQuery);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> reviewList, ParseException e) {
				if(e == null){
					for(ParseObject obj : reviewList){
						movies.add(JSONUtils.MovieParser.parseMovie(obj));
					}
					moviesAdapter = new MovieListViewAdapter(mListener.getContext(), R.layout.movie_list_item, movies);
					((ListView) getActivity().findViewById(R.id.friend_review_list)).setAdapter(moviesAdapter);	
				}
				else{
					Toast.makeText(mListener.getContext(), "No Shared Reviews Found", Toast.LENGTH_LONG).show();
					mListener.onBackPressed();
				}
			}
		});*/
		
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SharedReviews");
		query.whereEqualTo("sharedTo", ParseUser.getCurrentUser().getUsername());
		query.setLimit(1000);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> reviewsList, ParseException e) {
		        if (e == null) {
		            Log.d("score", "Retrieved " + reviewsList.size() + " shared");
		            if(reviewsList.size() > 0){
			            for(ParseObject obj : reviewsList){
			            	Log.d("demo", "MovId: " + obj.getInt("movieId"));
			            	movieIds.add(obj.getInt("movieId"));
				        }
			            loadMovies();

			    		moviesAdapter = new MovieListViewAdapter(mListener.getContext(), R.layout.movie_list_item, movies);
			    		((ListView) getView().findViewById(R.id.friend_review_list)).setAdapter(moviesAdapter);
			    		moviesAdapter.setNotifyOnChange(true);
			    		((ListView) getView().findViewById(R.id.friend_review_list)).setOnItemClickListener(new OnItemClickListener(){

							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

								
								getFragmentManager().beginTransaction()
						    	.replace(R.id.LinearLayout1, new MovieReviewFragment("friendReview",movies.get(position)),"movieReview")
						    	.addToBackStack(null)
						    	.commit();
							}
							
						});
		            }
		            else{
						Toast.makeText(mListener.getContext(), "No Shared Reviews Found", Toast.LENGTH_LONG).show();
						mListener.onBackPressed();
					}	
			        //adapter = new FriendListViewAdapter(mListener.getContext(), android.R.layout.simple_list_item_1, contacts, getFragmentManager());
			        //((ListView) getActivity().findViewById(R.id.contacts_list)).setAdapter(adapter);
			        
			        //alertDialog.dismiss();
		            
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
			}
		});
	}
	
	public void loadMovies(){
		for(int id : movieIds){
			Log.d("demo", "Number: " + friendNumber);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
			query.whereEqualTo("movieId", id);
			query.whereEqualTo("username", friendNumber);
			query.setLimit(1000);
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> reviewsList, ParseException e) {
			        if (e == null) {
			            Log.d("score", "Retrieved " + reviewsList.size() + " ReviewMovie");
			            for(ParseObject obj : reviewsList){
			            	Log.d("demo", "a" + (reviewsList == null));
			            	movies.add(JSONUtils.MovieParser.parseMovie(obj));
				        }
			            for(Movie m:movies)
			            {
			            	m.setReviewBy(friendNumber);
			            	m.setReviewerName(friendName);
			            }
				        //alertDialog.dismiss();
			            Log.d("demo", "Num Movies Reviews: " + movies.size());
			            moviesAdapter.notifyDataSetChanged();
			            
			        } else {
			            Log.d("score", "Error: " + e.getMessage());
			        }
				}
			});
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		mListener.setTitle(friendName + "'s Reviews");
	}
	
	

}
