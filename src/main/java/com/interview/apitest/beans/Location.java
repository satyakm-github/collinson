package com.interview.apitest.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
	@JsonProperty("city")
	String city;

	@JsonProperty("country")
	String country;

	@JsonProperty("latitude")
	double latitude;

	@JsonProperty("longitude")
	double longitude;

	public Location() {
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

}