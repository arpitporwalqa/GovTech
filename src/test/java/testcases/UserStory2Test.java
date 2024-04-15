package testcases;

import org.junit.Test;
import org.junit.runner.RunWith;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import steps.USSteps;

@RunWith(SerenityRunner.class)
public class UserStory2Test extends BaseTest {

	@Steps
	USSteps api;

	@Title("Validate the CSV File Format to be inserted")
	@Test
	public void validateCSVFileFormat() {
		api.validateCSVFileFormat("valid.csv");
	}

	@Title("Upload a CSV File via Clerk Portal")
	@Test
	public void uploadCSVByPortal() {
		openPage("http://localhost:9997/login");
		api.enterUsername("clerk");
		api.enterPassword("clerk");
		api.clickSubmitButton();
		api.validateNextPage("dropdownMenuButton2");
		api.clickDropdownButton();
		api.selectSecondOption();
		api.validateCSVFileScreenandClickChoseFile("valid.csv");
	}

	@Title("Validate the Success Notification and CSV Records in Database after uploading CSV file")
	@Test
	public void validateCSVFileRecords() {
		api.enterUsername("clerk");
		api.enterPassword("clerk");
		api.clickSubmitButton();
		api.validateNextPage("dropdownMenuButton2");
		api.clickDropdownButton();
		api.selectSecondOption();
		api.validateCSVFileScreenandClickChoseFile("valid.csv");
		api.validateSuccessNotification();
		api.erroneousRecordUIValidation();
	}

	@Title("Validate the erroneous record in the CSV file after upload")
	@Test
	public void validateErroneousCSVRecord() {
		openPage("http://localhost:9997/login");
		api.enterUsername("clerk");
		api.enterPassword("clerk");
		api.clickSubmitButton();
		api.validateNextPage("dropdownMenuButton2");
		api.clickDropdownButton();
		api.selectSecondOption();
		api.validateCSVFileScreenandClickChoseFile("invalid.csv");
		api.erroneousRecordUIValidation();
	}
}