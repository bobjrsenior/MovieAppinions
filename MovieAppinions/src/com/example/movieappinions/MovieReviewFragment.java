package com.example.movieappinions;

import java.util.List;

import com.example.movieappinions.MovieListFragment.OnFragmentInteractionListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link MovieReviewFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link MovieReviewFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */

public class MovieReviewFragment extends Fragment {

	TextView ratingValue, releaseDateValue, fullPlotValue, title,
			friendReviewValue;
	EditText reviewValue;
	Button submitReview,friendsReview;
	ImageView poster,previous,globe;
	ArrayAdapter<String> adapter;
	LinearLayout reviewDetails;
	AlertDialog.Builder alertDialogBuilder;
	AlertDialog alertDialog;
	List<ParseObject> userObj;
	Movie movie;
	String[] userNames;
	View layout;
	ImageView shareImage,saveImage;

	boolean share=true;
	int index;
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("here","is it");
		super.onActivityCreated(savedInstanceState);
		ratingValue = (TextView) getView().findViewById(R.id.ratingValue);
		// reviewValue=(EditText)getView().findViewById(R.id.reviewValue);
		releaseDateValue = (TextView) getView().findViewById(
				R.id.releaseDateValue);
		fullPlotValue = (TextView) getView().findViewById(R.id.fullPlotValue);
		title = (TextView) getView().findViewById(R.id.appTitle);
		poster = (ImageView) getView().findViewById(R.id.previewImage);
		shareImage = (ImageView) getView().findViewById(R.id.shareImage);
		saveImage = (ImageView) getView().findViewById(R.id.saveImage);
		previous = (ImageView) getView().findViewById(R.id.previousImage);
		globe = (ImageView) getView().findViewById(R.id.globeImage);
		submitReview=(Button)getView().findViewById(R.id.saveButton);
		friendsReview=(Button)getView().findViewById(R.id.friendsButton);
		

		title.setText(movie.getTitle());
		ratingValue.setText(movie.getRaRating()+"/100");
		// reviewValue.setText("my review");
		releaseDateValue.setText(movie.getYear());
		fullPlotValue.setText(movie.getPlot());
		Picasso.with(getActivity()).load(movie.getPosterURL()).into(poster);
		reviewDetails = (LinearLayout) getView().findViewById(
				R.id.reviewDetailsLayout);

		if (from.equals("main")) {
			reviewValue = new EditText(getView().getContext());
			reviewValue.setBackgroundResource(R.drawable.back);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
			query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
			 query.whereEqualTo("movieId",movie.getId());
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> saved, ParseException e) {
			        if (e == null) {
			            Log.d("saved", "Retrieved " + saved.size() + " details");
			            if(saved.size() >0)
			            {
			            	
			            	reviewValue.setText(saved.get(0).getString("review"));
			            	
			            	  
								
			            }
			            else
			            {
			            	
			            	reviewValue.setText("");
					        
						      
			            }
			        } else {
			            Log.d("error", "Error: " + e.getMessage());
			        }
			    }
			});
			reviewDetails.addView(reviewValue);
		} else {
			TextView reviewLabel=(TextView)getView().findViewById(R.id.review);
			reviewLabel.setBackgroundResource(R.drawable.back);
			reviewLabel.setText(movie.getReviewerName()+"'s Review");
			friendReviewValue = new TextView(getView().getContext());
			friendReviewValue.setBackgroundResource(R.drawable.back);
			friendReviewValue.setTextSize((float) 20.0);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
			query.whereEqualTo("username", movie.getReviewBy());
			 query.whereEqualTo("movieId",movie.getId());
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> saved, ParseException e) {
			        if (e == null) {
			            Log.d("saved", "Retrieved " + saved.size() + " details");
			            if(saved.size() >0)
			            {
			            	
			            	friendReviewValue.setText(saved.get(0).getString("review"));
			            	
			            	  
								
			            }
			            else
			            {
			            	
			            	friendReviewValue.setText("");
					        
						      
			            }
			        } else {
			            Log.d("error", "Error: " + e.getMessage());
			        }
			    }
			});
			reviewDetails.addView(friendReviewValue);
		}
		if(from.equals("friendReview"))
		{
			LinearLayout reviewButtonsLayout=(LinearLayout)getView().findViewById(R.id.reviewButtonsLayout);
			LinearLayout reviewIconLayout=(LinearLayout)getView().findViewById(R.id.reviewIconLayout);
			reviewButtonsLayout.setVisibility(View.GONE);
			reviewIconLayout.setVisibility(View.GONE);
		}
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
		query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
		 query.whereEqualTo("movieId",movie.getId());
		 query.whereEqualTo("favorite", true);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> saved, ParseException e) {
		        if (e == null) {
		            Log.d("saved", "Retrieved " + saved.size() + " details");
		            if(saved.size() >0)
		            {
		            	
		            	 saveImage.setImageDrawable(getResources().getDrawable(
									R.drawable.rating_important));
		            	
		            	  
							
		            }
		            else
		            {
		            	
		            	saveImage.setImageDrawable(getResources().getDrawable(
								R.drawable.rating_not_important));
				        
					      
		            }
		        } else {
		            Log.d("error", "Error: " + e.getMessage());
		        }
		    }
		});
		
		friendsReview.setOnClickListener(new  OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.showfriendsReview(movie.getId());
			}
			
		});
		submitReview.setOnClickListener(new  OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("string review value is", "s "+reviewValue.getText());
				if( !reviewValue.getText().toString().equals(""))
				{
            	ParseQuery<ParseObject> updateQuery = ParseQuery.getQuery("Reviews");
            	updateQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            	updateQuery.whereEqualTo("movieId",movie.getId());
            	
            	updateQuery.findInBackground(new FindCallback<ParseObject>() {
    				@Override
    				public void done(List<ParseObject> arg0, ParseException arg1) {
            	        if (arg1 == null) {
            	        	
            	        	if(arg0.size()>0)
            	        	{
            	        	
            	            for(ParseObject e : arg0){
            	            e.put("review", reviewValue.getText().toString());
            	            e.saveInBackground();
            	            }
            	        	}
            	        	else
            	        	{
            	        		ParseObject itemObj = new ParseObject("Reviews");
				            	
				            	itemObj.put("username", ParseUser.getCurrentUser().getUsername());
			 				       itemObj.put("review",reviewValue.getText().toString());
			 				    //   itemObj.put("sharedBy", ParseUser.getCurrentUser().getUsername());
			 				      itemObj.put("movieId",movie.getId());
			 				     itemObj.put("movieTitle",movie.getTitle());
			 				     itemObj.put("moviePlot",movie.getPlot());
			 				     itemObj.put("moviePosterUrl",movie.getPosterURL());
			 				     itemObj.put("movieRaRating",movie.getRaRating());
			 				     itemObj.put("movieRating",movie.getRating());
			 				    itemObj.put("movieReleaseYear",movie.getYear());
			 				   itemObj.put("movieThumbnailURL",movie.getThumbnailURL());
			 				 
						        itemObj.saveInBackground();
            	        	}

            	        } else {
            	        	  Toast.makeText(getActivity(), "Problem in retrieving the details", Toast.LENGTH_LONG).show();
						      
            	        }					
    				}
            	});
            	
            	
            
		        Toast.makeText(getActivity(), "Review stored successfully", Toast.LENGTH_LONG).show();
			}
				else
				{
					Toast.makeText(getActivity(), "Please enter your review...", Toast.LENGTH_LONG).show();
				}
            
			}
			
		});
		
		globe.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(movie.getRaURL() != null){
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(movie.getRaURL()));
					startActivity(i);
				}
				else{
					Toast.makeText(getActivity(), "The URL is not valid", Toast.LENGTH_LONG).show();
				}
			}
			
		});
		previous.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 getActivity().onBackPressed(); 
			}
			
		});
		
		saveImage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
				query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
				 query.whereEqualTo("movieId",movie.getId());
				 query.whereEqualTo("favorite", true);
				query.findInBackground(new FindCallback<ParseObject>() {
				    public void done(List<ParseObject> scoreList, ParseException e) {
				        if (e == null) {
				            Log.d("score", "Retrieved " + scoreList.size() + " scores");
				            if(scoreList.size() >0)
				            {
				            	ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
				    	        query.whereEqualTo("favorite", true);
				    			query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
								 query.whereEqualTo("movieId",movie.getId());
				    	         query.findInBackground(new FindCallback<ParseObject>() {
				    				@Override
				    				public void done(List<ParseObject> arg0, ParseException arg1) {
				            	        if (arg1 == null) {
				            	            for(ParseObject e : arg0){
				            	            	e.deleteInBackground();
				            	            }

				            	        } else {
				            	        	
				            	        }					
				    				}
				            	});
				    	       
				            	saveImage.setImageDrawable(getResources().getDrawable(
										R.drawable.rating_not_important));
				            	   Toast.makeText(getActivity(), "Removed from favourites", Toast.LENGTH_LONG).show();
									
				            }
				            else
				            {
				            	
				            	ParseQuery<ParseObject> updateQuery = ParseQuery.getQuery("Favorites");
				            	updateQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
				            	updateQuery.whereEqualTo("movieId",movie.getId());
				            	
				            	updateQuery.findInBackground(new FindCallback<ParseObject>() {
				    				@Override
				    				public void done(List<ParseObject> arg0, ParseException arg1) {
				            	        if (arg1 == null) {
				            	        	
				            	        	if(arg0.size()>0)
				            	        	{
				            	        	
				            	            for(ParseObject e : arg0){
				            	            e.put("favorite", true);
				            	            e.saveInBackground();
				            	            }
				            	        	}
				            	        	else
				            	        	{
				            	        		ParseObject itemObj = new ParseObject("Favorites");
								            	
								            	itemObj.put("username", ParseUser.getCurrentUser().getUsername());
							 				       itemObj.put("favorite",true);
							 				    //   itemObj.put("sharedBy", ParseUser.getCurrentUser().getUsername());
							 				      itemObj.put("movieId",movie.getId());
							 				     itemObj.put("movieTitle",movie.getTitle());
							 				     itemObj.put("moviePlot",movie.getPlot());
							 				     itemObj.put("moviePosterUrl",movie.getPosterURL());
							 				     itemObj.put("movieRaRating",movie.getRaRating());
							 				     itemObj.put("movieRating",movie.getRating());
							 				    itemObj.put("movieReleaseYear",movie.getYear());
							 				   itemObj.put("movieThumbnailURL",movie.getThumbnailURL());
							 				  itemObj.put("rottenLink",movie.getRaURL());
										        itemObj.saveInBackground();
				            	        	}

				            	        } else {
				            	        	  Toast.makeText(getActivity(), "Problem in retrieving the details", Toast.LENGTH_LONG).show();
										      
				            	        }					
				    				}
				            	});
				            	
				            	
				            	
				            	
						        saveImage.setImageDrawable(getResources().getDrawable(
										R.drawable.rating_important));
						        Toast.makeText(getActivity(), "Added to Favourites", Toast.LENGTH_LONG).show();
							      
				            }
				        } else {
				            Log.d("score", "Error: " + e.getMessage());
				        }
				    }
				});
				
			}
			
		});
		
		
		shareImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseQuery<ParseObject> Checkquery = ParseQuery.getQuery("Reviews");
				Log.d("current",ParseUser.getCurrentUser().getUsername());
				Checkquery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
				Checkquery.whereEqualTo("movieId", movie.getId());

				Checkquery.findInBackground(new FindCallback<ParseObject>() {
					public void done(List<ParseObject> objects,
							ParseException e) {
						if (e == null) {
							if(objects.size()==0)
							{
								share=false;
							}
							else
							{
								share=true;
							}
						}
					
				
				if(share)
				{
				alertDialogBuilder = new AlertDialog.Builder(getActivity());
				alertDialogBuilder.setTitle("Users");
				LayoutInflater inflater = getActivity().getLayoutInflater();

				layout = inflater.inflate(R.layout.layout_user_list, null);
				alertDialogBuilder.setView(layout);

				alertDialogBuilder.setCancelable(true);

				try {



					ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
					Log.d("current",ParseUser.getCurrentUser().getUsername());
					query.whereEqualTo("owner", ParseUser.getCurrentUser().getUsername());

					query.findInBackground(new FindCallback<ParseObject>() {
						public void done(List<ParseObject> objects,
								ParseException e) {
							if (e == null) {
								// The query was successful.
								userObj = objects;
								userNames = new String[objects.size()];
								for (int i = 0; i < objects.size(); i++) {
									Log.d("value is", (String) objects.get(i)
											.get("name"));
									userNames[i] = (String) objects.get(i).get(
											"name");
									Log.d("array", userNames[i]);

								}

								adapter = new ArrayAdapter<String>(
										getActivity(),
										android.R.layout.simple_list_item_1,
										userNames);
								adapter.setNotifyOnChange(true);

								ListView listView = (ListView) layout
										.findViewById(R.id.userList);
								listView.setAdapter(adapter);
								listView.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub
										index = position;
										try {

											ParseQuery<ParseObject> query = ParseQuery
													.getQuery("SharedReviews");
											query.whereEqualTo("sharedTo",
													userObj.get(index)
															.getString("phoneNum"));
											query.whereEqualTo("sharedBy",
													ParseUser.getCurrentUser()
															.getUsername());
											query.whereEqualTo("movieId",
													movie.getId());
											query.findInBackground(new FindCallback<ParseObject>() {
												public void done(
														List<ParseObject> sharedList,
														ParseException e) {
													if (e == null) {

														if (sharedList.size() > 0) {

															Toast.makeText(
																	getActivity(),
																	"Already shared with "
																			+ userNames[index]
																			,
																	Toast.LENGTH_LONG)
																	.show();
															alertDialog
																	.cancel();
														} else {
															ParseObject itemObj = new ParseObject(
																	"SharedReviews");
															// itemObj.put("textitem",
															// itemText.getText().toString());
															itemObj.put(
																	"sharedTo",
																	userObj.get(
																			index)
																			.getString("phoneNum"));
														
															itemObj.put(
																	"sharedBy",
																	ParseUser
																			.getCurrentUser()
																			.getUsername());
															itemObj.put(
																	"movieId",
																	movie.getId());
															

															itemObj.saveInBackground();
															alertDialog
																	.cancel();
															Toast.makeText(
																	getActivity(),
																	"Shared with "
																			+ userNames[index],
																	Toast.LENGTH_LONG)
																	.show();

														}
													} else {
														Log.d("list",
																"Error: "
																		+ e.getMessage());
													}
												}
											});
										}

										catch (Exception e) {
											Toast.makeText(
													getActivity(),
													"Problem in sharing",
													Toast.LENGTH_LONG).show();

										}

									}

								});
							} else {
								// Something went wrong.
							}
						}
					});

				} catch (Exception ex) {
					ex.printStackTrace();

				}
				alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
				else
				{
					Toast.makeText(getActivity(), "Please submit your review and then share it with your contacts", Toast.LENGTH_LONG).show();
				}
			}
		});
			}

		});
		
	}

	private String mParam2;
	private String from;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment MovieReviewFragment.
	 */
	// TODO: Rename and change types and number of parameters

	public MovieReviewFragment(String from, Movie movie) {
		// Required empty public constructor
		this.from = from;
		this.movie = movie;
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
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_movie_review, container,
				false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			// mListener.onFragmentInteraction(uri);
		}
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
	public void onResume() {
		super.onResume();
		mListener.setTitle("Movie Page");
	}
	
	

}
