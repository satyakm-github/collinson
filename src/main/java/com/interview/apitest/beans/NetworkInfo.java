package com.interview.apitest.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkInfo {
	@JsonIgnore
	@JsonProperty("company")
	String[] company;

	@JsonProperty("href")
	String href;

	@JsonProperty("id")
	String id;

	@JsonProperty("location")
	Location loc;

	@JsonProperty("name")
	String name;

	@JsonProperty("source")
	String source;

	@JsonProperty("gbfs_href")
	String gbfs_href;

	@JsonProperty("license")
	License license;

	public NetworkInfo() {

	}

	public Location getLoc() {
		return loc;
	}
}