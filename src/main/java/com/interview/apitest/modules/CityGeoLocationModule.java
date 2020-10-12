package com.interview.apitest.modules;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.apitest.beans.NetworkInfo;
import com.interview.apitest.beans.Wrapper;

import static com.interview.apitest.constants.APITestConstants.API_ENDPOINT;

public class CityGeoLocationModule {
	@SuppressWarnings("unchecked")
	public double[] getLongLatitude(String countryName, String cityName)
			throws JsonParseException, JsonMappingException, IOException {
		URL url = new URL(API_ENDPOINT);

		ObjectMapper objectMapper = new ObjectMapper();
		Wrapper<NetworkInfo> allNetworks = objectMapper.readValue(url, Wrapper.class);

		List<NetworkInfo> accountList = objectMapper.convertValue(allNetworks.getData(),
				new TypeReference<List<NetworkInfo>>() {
				});

		Map<String, String> countries = new HashMap<>();
		for (String iso : Locale.getISOCountries()) {
			Locale l = new Locale("", iso);
			countries.put(l.getDisplayCountry(), iso);
		}

		String deCode = countries.get(countryName);

		for (NetworkInfo info : accountList) {
			if (info.getLoc().getCity().equalsIgnoreCase(cityName)
					&& info.getLoc().getCountry().equalsIgnoreCase(deCode)) {
				return new double[] { info.getLoc().getLatitude(), info.getLoc().getLongitude() };
			}
		}
		return null;
	}
}