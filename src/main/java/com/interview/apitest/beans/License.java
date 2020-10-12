package com.interview.apitest.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class License {
	@JsonProperty("name")
	String name;

	@JsonProperty("url")
	String url;

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

}