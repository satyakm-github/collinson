package com.interview.apitest.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("hiding")
public class Wrapper<NetworkInfo> {
	@JsonProperty("networks")
	public List<NetworkInfo> data;

	public Wrapper() {
	}

	public Wrapper(List<NetworkInfo> data) {
		this.data = data;
	}

	public List<NetworkInfo> getData() {
		return data;
	}

	public void setData(List<NetworkInfo> data) {
		this.data = data;
	}
}
