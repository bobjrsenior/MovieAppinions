package com.example.movieappinions;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
	public static class MovieParser{
		/**
		 * Parses a movie from Rotten Tomatoes
		 * @return A String representing a Movie JSON Object 
		 * @throws JSONException
		 */
		public static Movie parseMovie(String in) throws JSONException{
			return parseMovie(new JSONObject(in));
		}
		
		/**
		 * Parses a movie from Rotten Tomatoes
		 * @return Movie JSON Object 
		 * @throws JSONException
		 */
		public static Movie parseMovie(JSONObject root) throws JSONException{
			Movie movie = new Movie();
			movie.setTitle(root.getString("title"));
			movie.setYear(root.getString("year"));
			movie.setRating(root.getString("mpaa_rating"));
			JSONObject ratings = root.getJSONObject("ratings");
			movie.setRaRating((int) ((ratings.getInt("audience_score") + ratings.getInt("critics_score")) * .5));
			movie.setPlot(root.getString("synopsis"));
			JSONObject posters = root.getJSONObject("posters");
			movie.setThumbnailURL(posters.getString("thumbnail"));
			//Get actual size out of poster url since it only links small sizes
			movie.setPosterURL(movie.getThumbnailURL());
			return movie;
		}
		
		/**
		 * Returns a list of Movie Objects based on data retrieved from Rotten Tomatoes
		 * @param in String representing the Rotten TOmatoes Data
		 * @return A list of movie objects
		 * @throws JSONException
		 */
		public static ArrayList<Movie> parseMovies(String in) throws JSONException{
			ArrayList<Movie> movies = new ArrayList<Movie>();
			JSONObject root = new JSONObject(in);
			JSONArray moviesArr = root.getJSONArray("movies");
			for(int e = 0; e < moviesArr.length(); e ++){
				movies.add(parseMovie(moviesArr.getJSONObject(e)));
			}
			return movies;
		}
	}
}
