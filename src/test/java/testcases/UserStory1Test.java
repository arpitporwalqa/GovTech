package testcases;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import steps.USSteps;
import io.restassured.response.Response;

@RunWith(SerenityRunner.class)
public class UserStory1Test extends BaseTest {

	@Steps
	USSteps api;
	Response response;
	String natid = "natid-12";
	String name = "hello";
	String gender = "MALE";
	String birthDate = "2020-01-01T23:59:59";
	String deathDate = null;
	double salary = 10.00;
	double taxPaid = 1;
	int browniePoints = 9;

	@Title("Send POST Request on /api/v1/hero with the payload")
	@Test
	public void postHeroRequestWithPayload() {
		response = api.sendPostRequest(natid, name, gender, birthDate, deathDate, salary, taxPaid, browniePoints);
	}

	@Title("Perform the Field Validations on the /api/v1/hero response")
	@Test
	public void performFieldValidations() {
		api.validateResponse(response, natid, name, gender, birthDate, deathDate, salary, taxPaid, browniePoints);
	}

	@Title("Verify the Natid existence in the database after sending POST request")
	@Test
	public void verifyNatidExists() {
		boolean isExists = api.implementNatidExistence("natid-12");
		if (isExists) {
			System.out.println("Natid exists in the working_class_heroes table.");
		} else {
			System.out.println("Natid does not exist in the working_class_heroes table.");
		}
	}

	@Title("Verify if the record has been created in the database table after sending POST request")
	@Test
	public void verifyDatabaseRecordCreation() {
		boolean isExists = api.implementNatidExistence("natid-12");
		if (isExists) {
			System.out.println("Record for Natid exists in the working_class_heroes table.");
		} else {
			System.out.println("Record for Nat id does not exist in the working_class_heroes table.");
		}
	}
}