package com.example.movieappinions;

import com.parse.ParseObject;

public class Contact {
	
	private String name;
	private String number;
	private int reviews;
	
	public Contact(String name, String number, int reviews) {
		this.name = name;
		this.number = number;
		this.reviews = reviews;
	}
	public Contact(String name, String number) {
		this.name = name;
		this.number = number;
	}
	
	public Contact(ParseObject obj){
		this.name = obj.getString("name");
		this.number = obj.getString("phoneNum");
		this.reviews = 0;//obj.getInt("reviews");
	}
	
	public Contact(){
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getReviews() {
		return reviews;
	}
	public void setReviews(int reviews) {
		this.reviews = reviews;
	}
	@Override
	public String toString() {
		return name + "  " + reviews;
	}
	
	
}
