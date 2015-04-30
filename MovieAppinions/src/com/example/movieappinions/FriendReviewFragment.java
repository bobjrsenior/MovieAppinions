package com.example.movieappinions;

import com.example.movieappinions.MovieListFragment.OnFragmentInteractionListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
	private TextView friendText;

	public FriendReviewFragment(String friendName) {
		super();
		this.friendName = friendName;
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
	}
	
	

}
