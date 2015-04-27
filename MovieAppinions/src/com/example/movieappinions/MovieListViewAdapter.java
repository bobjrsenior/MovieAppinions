package com.example.movieappinions;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Used to create the custom List View items to display movies
 * The standard adapter can't handle more complex list item layouts
 */
public class MovieListViewAdapter  extends ArrayAdapter<Movie> {

	Context c;
	static List<Movie> movies;
	int resources;

	public MovieListViewAdapter(Context c, int resources, List<Movie> movies) {
		super(c, R.layout.movie_list_item, movies);
		this.c = c;
		MovieListViewAdapter.movies = movies;
		this.resources = resources;
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
		((TextView) convertView.findViewById(R.id.movie_title)).setText(movies.get(position).getTitle());
		((TextView) convertView.findViewById(R.id.movie_release_date)).setText(movies.get(position).getYear());
		((TextView) convertView.findViewById(R.id.movie_rating)).setText(movies.get(position).getRating());
		((TextView) convertView.findViewById(R.id.movie_ra_rating)).setText(movies.get(position).getRaRating());
		((TextView) convertView.findViewById(R.id.movie_plot)).setText(movies.get(position).getPlot());
		Picasso.with(c).load(movies.get(position).getThumbnailURL()).error(R.drawable.ic_launcher).into((ImageView) convertView.findViewById(R.id.movie_poster));
		
		return convertView;
	}

}
