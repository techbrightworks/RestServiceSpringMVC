package org.srinivas.siteworks.cucumber.changeservice;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.LinkedHashMap;
import java.util.List;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ChangeServiceStepDefinition {

	private static final String CHANGE_OPTIMAL_URI = "/change/optimal";
	private static final String CHANGE_SUPPLY_URI = "/change/supply";
	private Response response;
	private RequestSpecification request;
	private String RESTSERVICE_BASEPATH_URI = "http://localhost:9090/RestServiceSpringMVC";
	private String endPoint;

	@Given("^I want change for (\\d+)$")
	public void i_want_change_for(int arg1) throws Exception {
		request = given().queryParam("pence", arg1);
		request.log().all();
	}

	@Given("^I choose Optimal change$")
	public void i_choose_Optimal_change() throws Exception {
		setEndPoint(RESTSERVICE_BASEPATH_URI + CHANGE_OPTIMAL_URI);
	}

	@When("^I call ChangeService for optimal change$")
	public void i_call_ChangeService_for_optimal_change() throws Exception {
		response = request.when().get(getEndPoint());
		assertEquals(200, response.statusCode());
		response.prettyPrint();
	}

	@SuppressWarnings("unchecked")
	@Then("^I validate the Optimal outcomes$")
	public void i_validate_the_Optimal_outcomes() throws Exception {
		XmlMapper xmlMapper = new XmlMapper();
		List<LinkedHashMap<String, String>> coins = (List<LinkedHashMap<String, String>>) xmlMapper
				.readValue(response.xmlPath().prettify(), List.class);
		assertTrue(coins.size() == 5);
		assertEquals("5", coins.stream().filter(e -> e.get("value").equals("100")).findFirst().get().get("count"));
		assertEquals("1", coins.stream().filter(e -> e.get("value").equals("50")).findFirst().get().get("count"));
		assertEquals("1", coins.stream().filter(e -> e.get("value").equals("20")).findFirst().get().get("count"));
		assertEquals("1", coins.stream().filter(e -> e.get("value").equals("5")).findFirst().get().get("count"));
		assertEquals("1", coins.stream().filter(e -> e.get("value").equals("1")).findFirst().get().get("count"));
	}

	@Given("^I choose Supply change$")
	public void i_choose_Supply_change() throws Exception {
		setEndPoint(RESTSERVICE_BASEPATH_URI + CHANGE_SUPPLY_URI);
	}

	@When("^I call ChangeService for supply change$")
	public void i_call_ChangeService_for_supply_change() throws Exception {
		response = request.when().get(getEndPoint());
		assertEquals(200, response.statusCode());
		response.prettyPrint();
	}

	@SuppressWarnings("unchecked")
	@Then("^I validate the Supply outcomes$")
	public void i_validate_the_Supply_outcomes() throws Exception {
		XmlMapper xmlMapper = new XmlMapper();
		List<LinkedHashMap<String, String>> coins = (List<LinkedHashMap<String, String>>) xmlMapper
				.readValue(response.xmlPath().prettify(), List.class);
		assertTrue(coins.size() == 5);
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

}
