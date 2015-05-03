package com.example.movieappinions;

import java.util.List;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendListViewAdapter extends ArrayAdapter<Contact> {
	Context c;
	static List<Contact> friends;
	int resources;
	private FragmentManager fragmentManager;

	public FriendListViewAdapter(Context c, int resources, List<Contact> friends, FragmentManager fragmentManager) {
		super(c, R.layout.friends_list_item, friends);
		this.c = c;
		FriendListViewAdapter.friends = friends;
		Log.d("demo", ":" + friends.size());
		this.resources = resources;
		this.fragmentManager = fragmentManager;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater layoutView = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = layoutView.inflate(R.layout.friends_list_item,
					parent, false);
		}
		
		//Populate the view
		((TextView) convertView.findViewById(R.id.friend)).setText(friends.get(position).getName());
		convertView.setOnClickListener(new onClickList(position));
		return convertView;
	}
	
	public class onClickList implements OnClickListener{

		private int position;
		
		public onClickList(int position) {
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			fragmentManager.beginTransaction()
			.replace(R.id.LinearLayout1, new FriendReviewFragment(friends.get(position).getName(), friends.get(position).getNumber()), "friend_review_list")
			.addToBackStack("friend_review_list")
			.commit();
		}
	}
}
