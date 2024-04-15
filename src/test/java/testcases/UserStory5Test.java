package testcases;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import steps.USSteps;

@RunWith(SerenityRunner.class)
public class UserStory5Test extends BaseTest {

	@Steps
	USSteps api;
	String endpoint = "/api/v1/hero/owe-money";
	String natID = "123";
	Response response;

	@Title("Send Get Request to validate if working class owe money and validate Responses")
	@Test
	public void getHeroClassOweMoney() {
		response = api.makeGetRequest(endpoint, natID);
		//Validate Query Parameter is a integer
		Assert.assertTrue(api.isInteger(natID));
		//Validate Response Format
		api.validateResponseFormat(response);
		//Validate Response Content
		api.validateResponseContent(response, natID);
	}

}