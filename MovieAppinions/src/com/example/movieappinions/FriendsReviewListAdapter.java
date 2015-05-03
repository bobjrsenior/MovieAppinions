package com.example.movieappinions;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendsReviewListAdapter extends ArrayAdapter<Contact>  {
	Context c;
	List<Contact> friends;
	TextView friendsReviewListName,noOfReviews;
	private ArrayList<Movie> movies;
	Integer count[]=new Integer[100];
	int resources;
	int index;
	String from;
	int movieId;
	private FragmentManager fragmentManager;
	
	public FriendsReviewListAdapter(Context c, int resources, List<Contact> friends, FragmentManager fragmentManager,String from, int movieId) {
		super(c, R.layout.friends_review_list_layout, friends);
		this.c = c;
		this.friends = friends;
		this.resources = resources;
		this.from=from;
		this.movieId=movieId;
		this.fragmentManager = fragmentManager;
		
		for(int i=0;i<100;i++)
			count[i]=0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		index=position;
		if (convertView == null) {
			LayoutInflater layoutView = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = layoutView.inflate(R.layout.friends_review_list_layout,
					parent, false);
		}
		Log.d("demo", "in getView");

		friendsReviewListName = (TextView) convertView.findViewById(R.id.friendsReviewListName);
		noOfReviews = (TextView) convertView.findViewById(R.id.noOfReviews);
		Log.d("cmg value is"," check "+friends.get(position).getName());
		friendsReviewListName.setText(friends.get(position).getName());
		
		noOfReviews.setText(" "+friends.get(position).getReviews());
		convertView.setOnClickListener(new onClickList(position));
//		for(ParseObject temp:friends)
//		{
//			Log.d("size ","is "+friends.size());
//			if(friends.size()==0)
//			{
//				
//			}
//			else
//			{
//				if(temp.getString("name")==friends.get(position).getString("name") && (count[position]==0 || count[position]==null))
//				{
//					count[position]=1;
//
//					Log.d("value is","is for "+friends.get(position).getString("name"));
//					
//					friendsReviewListName.setText(friends.get(position).getString("name"));
//					Log.d("noofreview","is "+count[position]);
//					noOfReviews.setText(count[position].toString());
//				}
//				else
//				{
//					count[position]++;
//					Log.d("noofreview","is "+count[position]);
//					noOfReviews.setText(count[position].toString());
//				}
//			}
//		}
//		
		return convertView;
	}
	
	public class onClickList implements OnClickListener{

		private int position;
		
		public onClickList(int position) {
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			Log.d("demo", "Click");
			if(!from.equals("review"))
			{
			fragmentManager.beginTransaction()
			.replace(R.id.LinearLayout1, new FriendReviewFragment(friends.get(position).getName(), friends.get(position).getNumber()), "friend_review_list")
			.addToBackStack("friend_review_list")
			.commit();
			}
			else
			{
				
				ParseQuery<ParseObject> friendNameQuery = ParseQuery.getQuery("Reviews");
	    		friendNameQuery.whereEqualTo("username", friends.get(position).getNumber());
	    		friendNameQuery.whereEqualTo("movieId", movieId);
				 friendNameQuery.findInBackground(new FindCallback<ParseObject>() {
				    public void done(List<ParseObject> saved, ParseException e) {
				        if (e == null) {
				        	movies = new ArrayList<Movie>();
				        	  for(ParseObject obj : saved){
					            	Log.d("demo", "a" + (saved == null));
					            	movies.add(JSONUtils.MovieParser.parseMovie(obj));
						        }
				        	  for(Movie m:movies)
					            {
					            	m.setReviewBy(friends.get(position).getNumber());
					            	m.setReviewerName(friends.get(position).getName());
					            }
				        	  fragmentManager.beginTransaction()	
    	.replace(R.id.LinearLayout1, new MovieReviewFragment("friendReview",movies.get(0)),"movieReview")
    	.addToBackStack("friend_review_list")
				  			.commit();
				        }
				    }
				 }
			);
			}
		}
	}

}
