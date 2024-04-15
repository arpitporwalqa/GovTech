package testcases;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import steps.USSteps;

@RunWith(SerenityRunner.class)
public class UserStory4Test extends BaseTest {

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
	String voucherName = "VOUCHER 1";
	String voucherType = "TRAVEL";

	@Title("Send POST Request with the Voucher payload")
	@Test
	public void postHeroRequestWithVoucherPayload() {
		response = api.sendPostRequestVouchers(natid, name, gender, birthDate, deathDate, salary, taxPaid,
				browniePoints, voucherName, voucherType);
	}

	@Title("Perform the Field Validations on the Voucher payload")
	@Test
	public void performFieldValidations() {
		api.validateResponseVouchers(response, natid, name, gender, birthDate, deathDate, salary, taxPaid,
				browniePoints, voucherName, voucherType);
	}

	@Title("Validate the voucher creation in the database")
	@Test
	public void voucherValidationDatabase() {
		api.validateResponseVouchers(response, natid, name, gender, birthDate, deathDate, salary, taxPaid,
				browniePoints, voucherName, voucherType);
		String responseBody = response.getBody().asString();
		boolean vouchersNotNullOrEmpty = responseBody.contains("\"vouchers\":");
		boolean vouchersCreatedInDatabase = api.validateVouchersInDatabase();
		assertFalse(!vouchersNotNullOrEmpty || !vouchersCreatedInDatabase);
	}
}