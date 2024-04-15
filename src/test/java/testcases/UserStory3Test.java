package testcases;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import steps.USSteps;

@RunWith(SerenityRunner.class)
public class UserStory3Test extends BaseTest {

	@Steps
	USSteps api;

	@Title("Validate the Generate Tax Relief File Button functionality")
	@Test
	public void generateTaxReliefFileButton() {
		openPage("http://localhost:9997/login");
		api.enterUsername("bk");
		api.enterPassword("bk");
		api.clickSubmitButton();
		api.validateNextPage("tax_relief_btn");
		api.clickTaxReliefFileButton();
		api.validateFileExists("tax_relief_file.txt");
		api.validateCSVFileScreenandClickChoseFile("valid.csv");
	}

	@Title("Validate the content of generated text file")
	@Test
	public void generatedTaxFileValidation() {
		String downloadsFolderPath = System.getProperty("user.home") + "/Downloads";
		String filePath = downloadsFolderPath + "/" + "tax_relief_file.txt";
		validateTextFile(filePath);
	}

	@Title("Validate the footer if no records are found in generated tax file")
	@Test
	public void onlyFooterFile() {
		String downloadsFolderPath = System.getProperty("user.home") + "/Downloads";
		String filePath = downloadsFolderPath + "/" + "tax_relief_file.txt";
		validateFooterIfNoRecords(filePath);
	}

}