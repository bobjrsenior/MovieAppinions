package com.example.movieappinions;

/**
 * Hold data about a movie retrieved from Rotten Tomatoes
 */
public class Movie {

	public int id;
	public String title;
	public String year;
	public String rating;
	public int raRating;
	public String plot;
	public String thumbnailURL;
	public String posterURL;
	public String raURL;
	public String reviewBy;
	public String reviewerName;
	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public String getReviewBy() {
		return reviewBy;
	}

	public void setReviewBy(String reviewBy) {
		this.reviewBy = reviewBy;
	}

	public Movie() {
		
	}

	public Movie(int id, String title, String year, String rating, int raRating,
			String plot, String thumbnailURL, String posterURL, String raURL) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.rating = rating;
		this.raRating = raRating;
		this.plot = plot;
		this.thumbnailURL = thumbnailURL;
		this.posterURL = posterURL;
		this.raURL = raURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getRaRating() {
		return raRating;
	}

	public void setRaRating(int raRating) {
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

	public String getRaURL() {
		return raURL;
	}

	public void setRaURL(String raURL) {
		this.raURL = raURL;
	}
}
