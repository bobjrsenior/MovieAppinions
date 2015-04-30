package com.example.movieappinions;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieappinions.MovieListFragment.OnFragmentInteractionListener;
import com.example.movieappinions.R.id;
import com.squareup.picasso.Picasso;

public class FriendListViewAdapter extends ArrayAdapter<Contact> {
	Context c;
	static List<Contact> friends;
	int resources;
	private FragmentManager fragmentManager;
	private int position_cur;
	
	public FriendListViewAdapter(Context c, int resources, List<Contact> friends, FragmentManager fragmentManager) {
		super(c, R.layout.friends_list_item, friends);
		this.c = c;
		FriendListViewAdapter.friends = friends;
		this.resources = resources;
		this.fragmentManager = fragmentManager;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater layoutView = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = layoutView.inflate(R.layout.movie_list_item,
					parent, false);
		}
		
		//Populate the view
		((TextView) convertView.findViewById(R.id.friend)).setText(friends.get(position).getName());
		position_cur = position;
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fragmentManager.beginTransaction()
				.replace(R.id.LinearLayout1, new FriendReviewFragment(friends.get(position_cur).getName()), "friend_review_list")
				.addToBackStack("friend_review_list")
				.commit();
			}
		});
		return convertView;
	}
}
