package collinson;

import com.interview.apitest.modules.CityGeoLocationModule;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps {
	CityGeoLocationModule locationService = null;
	private String countryName;
	private String cityName;

	@When("^users provide city and country name$")
	public void users_provide_city_and_country_name() throws Throwable {
		locationService = new CityGeoLocationModule();
	}

	@When("^User enters \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_enters_and(String arg1, String arg2) throws Throwable {
		this.countryName = arg1;
		this.cityName = arg2;
	}

	@Then("^the server should handle it and return a long and lati values$")
	public void the_server_should_handle_it_and_return_a_long_and_lati_values() throws Throwable {
		double[] result = locationService.getLongLatitude(countryName, cityName);
		System.out.println("Geo Location of : " + countryName + " & " + cityName);
		System.out.println("Longitude: " + result[0]);
		System.out.println("Latitude : " + result[1]);
	}
}
