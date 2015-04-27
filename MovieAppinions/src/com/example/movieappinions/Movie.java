package com.example.movieappinions;

/**
 * Hold data about a movie retrieved from Rotten Tomatoes
 *
 */
public class Movie {

	public String title;
	public String year;
	public String rating;
	public String raRating;
	public String plot;
	public String thumbnailURL;
	public String posterURL;
	
	public Movie() {
		
	}

	public Movie(String title, String year, String rating, String raRating,
			String plot, String thumbnailURL, String posterURL) {
		super();
		this.title = title;
		this.year = year;
		this.rating = rating;
		this.raRating = raRating;
		this.plot = plot;
		this.thumbnailURL = thumbnailURL;
		this.posterURL = posterURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRaRating() {
		return raRating;
	}

	public void setRaRating(String raRating) {
		this.raRating = raRating;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	public String getPosterURL() {
		return posterURL;
	}

	public void setPosterURL(String posterURL) {
		this.posterURL = posterURL;
	}
}
