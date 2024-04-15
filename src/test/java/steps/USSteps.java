package steps;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.annotations.findby.By;
import testcases.BaseTest;
import static io.restassured.RestAssured.given;

public class USSteps extends BaseTest {

	private Response response;


	@Step("Send POST request to /api/v1/hero")
	public Response sendPostRequest(String natid, String name, String gender, String birthDate, String deathDate,
			double salary, double taxPaid, int browniePoints) {
		return RestAssured.given().contentType(ContentType.JSON)
				.body("{\n" + "    \"natid\": \"" + natid + "\",\n" + "    \"name\": \"" + name + "\",\n"
						+ "    \"gender\": \"" + gender + "\",\n" + "    \"birthDate\": \"" + birthDate + "\",\n"
						+ "    \"deathDate\": " + deathDate + ",\n" + "    \"salary\": " + salary + ",\n"
						+ "    \"taxPaid\": " + taxPaid + ",\n" + "    \"browniePoints\": " + browniePoints + "\n"
						+ "}")
				.when().post("/api/v1/hero").then().extract().response();
	}

	@Step("Validate the response from /api/v1/hero")
	public void validateResponse(Response response, String natid, String name, String gender, String birthDate,
			String deathDate, double salary, double taxPaid, int browniePoints) {
		response.then().statusCode(200).body("natid", equalTo(natid)).body("name", equalTo(name))
				.body("gender", equalTo(gender)).body("birthDate", equalTo(birthDate))
				.body("deathDate", equalTo(deathDate)).body("salary", equalTo((float) salary))
				.body("taxPaid", equalTo((float) taxPaid)).body("browniePoints", equalTo(browniePoints));
	}

	@Step("Send POST request to /api/v1/hero/vouchers")
	public Response sendPostRequestVouchers(String natid, String name, String gender, String birthDate, String deathDate,
			double salary, double taxPaid, int browniePoints, String voucherName, String voucherType) {
		return RestAssured.given().contentType(ContentType.JSON)
				.body("{\n" +
		                "\"natid\": \"" + natid + "\",\n" +
		                "\"name\": \"" + name + "\",\n" +
		                "\"gender\": \"" + gender + "\",\n" +
		                "\"birthDate\": \"" + birthDate + "\",\n" +
		                "\"deathDate\": " + deathDate + ",\n" +
		                "\"salary\": " + salary + ",\n" +
		                "\"taxPaid\": " + taxPaid + ",\n" +
		                "\"browniePoints\": " + browniePoints + ",\n" +
		                "\"vouchers\": [\n" +
		                "{\n" +
		                "\"voucherName\": \"" + voucherName + "\",\n" +
		                "\"voucherType\": \"" + voucherType + "\"\n" +
		                "}\n" +
		                "]\n" +
		                "}")
				.when().post("/api/v1/hero/vouchers").then().extract().response();
	}
	
	
	@Step("Validate the response from /api/v1/hero/vouchers")
	public void validateResponseVouchers(Response response, String natid, String name, String gender, String birthDate,
			String deathDate, double salary, double taxPaid, int browniePoints, String voucherName, String voucherType ) {
		response.then().statusCode(200).body("natid", equalTo(natid)).body("name", equalTo(name))
				.body("gender", equalTo(gender)).body("birthDate", equalTo(birthDate))
				.body("deathDate", equalTo(deathDate)).body("salary", equalTo((float) salary))
				.body("taxPaid", equalTo((float) taxPaid)).body("browniePoints", equalTo(browniePoints))
				.body("voucherName", equalTo(voucherName)).body("voucherType", equalTo(voucherType));
	}
	
	@Step("Validate the creation of Hero class if voucher is null")
	public boolean validateVouchersInDatabase() {
        boolean vouchersCreated = false;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {connection = getConnection();
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT COUNT(*) FROM VOUCHERS"); 
        	if (resultSet.next()) {
                int count = resultSet.getInt(1);
                vouchersCreated = count > 0;
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
         finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return vouchersCreated;
    }
	
	@Step("natid existence in the database")
	public boolean implementNatidExistence(String natid) {
		String query = "SELECT * FROM working_class_heroes WHERE natid = '" + natid + "'";
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Step("Enter username: {0}")
	public void enterUsername(String username) {
		getDriver().findElement(By.id("username-in")).sendKeys(username);
	}

	@Step("Enter password: {0}")
	public void enterPassword(String password) {
		getDriver().findElement(By.id("password-in")).sendKeys(password);
	}

	@Step("Click on Submit button")
	public void clickSubmitButton() {
		getDriver().findElement(By.xpath("//input[@type='submit']")).click();
	}

	@Step("Validate next page")
	public void validateNextPage(String locator) {
		boolean isElementPresent = getDriver().findElement(By.id(locator)).isDisplayed();
		Assert.assertTrue(isElementPresent);
	}

	@Step("Click on Generate Tax Relief Button")
	public void clickTaxReliefFileButton() {
		waitTillPresenceofElement(find(By.id("tax_relief_btn")));
		WebElement element = getDriver().findElement(By.id("tax_relief_btn"));
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
	}
	
	@Step("Click on dropdown menu button")
	public void clickDropdownButton() {
		getDriver().findElement(By.id("dropdownMenuButton2")).click();
	}

	@Step("Select 2nd option from dropdown menu")
	public void selectSecondOption() {
		getDriver().findElement(By.cssSelector("div.dropdown li:nth-child(2)")).click();
	}

	@Step("Upload CSV File Screen Display")
	public void validateCSVFileScreenandClickChoseFile(String fileName) {
		boolean isElementPresent = getDriver().findElement(By.id("upload-csv-file")).isDisplayed();
		Assert.assertTrue(isElementPresent); 
		System.out.print("Present Value" + isElementPresent);
		WebElement element = getDriver().findElement(By.id("upload-csv-file"));
		clickButton2secs(getDriver(), element);
		uploadCSVFile(element, fileName);
	}

	@Step("Upload CSV File Screen Display")
	public void validateCSVFileFormat(String fileName) {
		String projectPath = System.getProperty("user.dir");
		String filePath = projectPath + "/" + fileName;
		boolean isValidFormat = validateCSVFormat(filePath);
		if (isValidFormat) {
			System.out.println("CSV file format is valid");
		} else {
			System.out.println("CSV file format is invalid");
		}
	}

	@Step("Validate the Success Notification on CSV upload")
	public void validateSuccessNotification() {
		String successMessage = getDriver().findElement(By.xpath("//div[@class='bg-success pt-2']/h3")).getText();
		assertEquals("Created Successfully!", successMessage);
	}

	@Step("Verify record is created in database table WORKING_CLASS_HEROES")
	public void doesRecordExist() {
		boolean recordsPersisted = validateUploadedRecords();
		if (recordsPersisted) {
			System.out.println("Records are persisted successfully in the database table WORKING_CLASS_HEROES");
		} else {
			System.out.println("Failed to persist records in the database table WORKING_CLASS_HEROES");
		}
	}

	@Step("Erraneous Record Validation")
	public void erroneousRecordUIValidation() {
		String erraneousErrorHeading = getDriver().findElement(By.xpath("//div[@class='bg-warning pt-2']/h3"))
				.getText();
		assertEquals("Unable to create hero!", erraneousErrorHeading);
		String erraneousErrorBody = getDriver().findElement(By.xpath("//div[@class='col-12']/p")).getText();
		assertEquals("Unable to process csv file! Please contact tech support for help!", erraneousErrorBody);
	}
	
	@Step("Validate File Existence")
	public void validateFileExists(String fileName) {
	 waitForFileDownload();
    boolean fileExists = verifyFileExists(fileName);
    if (fileExists) {
        System.out.println("File has been generated successfully.");
    } else {
        System.out.println("File generation failed or file not found.");
    }
  }
	
	@Step("Send GET request to /owe-money")
	public Response makeGetRequest(String endpoint, String natID) {
        return RestAssured.given().queryParam("natid", natID).get(endpoint);
    }
	
	@Step("Validate the Response Format")
	public void validateResponseFormat(Response response) {
        Assert.assertEquals(response.statusCode(), 200);
        String responseBody = response.getBody().asString();
        Assert.assertTrue("Response payload format is invalid", responseBody.contains("data") && responseBody.contains("status"));
    }

	@Step("Validate the Response Content")
    public void validateResponseContent(Response response, String natID) {
        String messageData = response.jsonPath().getString("message.data");
        String messageStatus = response.jsonPath().getString("message.status");
        String timestamp = response.jsonPath().getString("timestamp");

        Assert.assertEquals(messageData, "natid-" + natID, "Incorrect data in response");
        Assert.assertTrue("Invalid status in response", messageStatus.equals("OWE") || messageStatus.equals("NIL"));
        Assert.assertNotNull(timestamp, "Timestamp should not be null");
    }

	@Step("Validate if Query Parameter is a Integer?")
	public boolean isInteger(String ID) {
        try {
            Integer.parseInt(ID);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	@Step("Voucher Insight Validation")
	public void validateVoucherInsightAPIResponse() {
        String endpoint = "/api/v1/voucher/by-person-and-type";
        response = 
        		given().when()
            .get(endpoint)
            .then()
            .extract()
            .response();
        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> data = jsonPath.getList("data");
        Assert.assertNotNull(data);
        Assert.assertFalse(data.isEmpty());
        for (Map<String, Object> entry : data) {
            Assert.assertTrue(entry.containsKey("name"));
            Assert.assertTrue(entry.containsKey("voucherType"));
            Assert.assertTrue(entry.containsKey("count"));
            int count = (int) entry.get("count");
            Assert.assertTrue(count >= 0);
        }
    }
	
}
