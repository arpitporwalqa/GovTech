package testcases;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import au.com.bytecode.opencsv.CSVReader;
import io.restassured.RestAssured;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class BaseTest extends PageObject{

	private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "userpassword";
	
    @Managed
    WebDriver driver;

    public void openPage(String url) {
        getDriver().get(url);
        getDriver().manage().window().maximize();
    }

	@BeforeClass
	public static void init() {
		RestAssured.baseURI="http://localhost:9997/login";
	}
	
	public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
	
	public static void clickButton2secs(WebDriver driver, WebElement element) {
    Actions actions = new Actions(driver);
    actions.clickAndHold(element).perform();
    try {
        Thread.sleep(5000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    actions.release().perform();
	}
	
	public static void uploadCSVFile(WebElement element, String fileName) {
		String projectPath = System.getProperty("user.dir");
		System.out.println("File Path --> "+ projectPath);
        String filePath = projectPath + "/" +fileName;
        element.sendKeys(filePath);
     }
	
	public static boolean validateCSVFormat(String filePath) {
        try 
        
        (CSVReader reader = new CSVReader(new FileReader(filePath))) 
        {  
            String[] headers = reader.readNext();
            if (headers == null) {
                System.out.println("CSV file is empty");
                return false;
            }
            if (!headers[0].isEmpty() || !headers[1].isEmpty()|| !headers[2].isEmpty()) {
               
                System.out.println("Incorrect CSV file headers");
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false; 
        }
     }
	
	 public boolean validateUploadedRecords() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM WORKING_CLASS_HEROES WHERE upload_timestamp >= TIMESTAMP(CURRENT_DATE)";
            ResultSet resultSet = statement.executeQuery(query);
            boolean recordsFound = resultSet.next();
            return recordsFound;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    
    }
	 
	 
	 public static void waitForFileDownload() {
	        try {
	            TimeUnit.SECONDS.sleep(10);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    public static boolean verifyFileExists(String fileName) {
	        String downloadsFolderPath = System.getProperty("user.home") + "/Downloads";
	        Path filePath = Paths.get(downloadsFolderPath, fileName);
	        return Files.exists(filePath);
	    }
	    public void waitTillPresenceofElement(WebElementFacade element) {
	    	 WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
	         wait.until(ExpectedConditions.elementToBeClickable(element));     
     }
	    
	    public void validateTextFile(String filePath) {
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            int recordCount = 0;

	            while ((line = br.readLine()) != null) {
	                String[] tokens = line.split(",");

	                if (tokens.length == 2) {
	                    String natid = tokens[0].trim();
	                    String taxReliefAmount = tokens[1].trim();
	                    if (natid.startsWith("natid")) {
	                        System.out.println("Validation passed: natid format is correct --> "+natid);
	                    } else {
	                        System.out.println("Validation failed: natid format is incorrect --> "+natid);
	                    }

	                    try {
	                        double taxAmount = Double.parseDouble(taxReliefAmount);
	                        System.out.println("Validation passed: taxReliefAmount is a floating-point number --> "+taxAmount);
	                    } catch (NumberFormatException e) {
	                        System.out.println("Validation failed: taxReliefAmount is not a floating-point number");
	                    }
	                    recordCount++;
	                }
	            }

	            String footerLine = br.readLine();
	            if (footerLine != null) {
	                int totalRecords = Integer.parseInt(footerLine.trim());

	                if (totalRecords > 0 && totalRecords == recordCount) {
	                    System.out.println("Validation passed: Footer is an integer greater than 0 and matches actual records");
	                } else {
	                    System.out.println("Validation failed: Footer is not an integer greater than 0 or does not match actual records");
	                }
	            } else {
	                System.out.println("Validation failed: Footer line not found");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	

public void validateFooterIfNoRecords(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;

        while ((line = br.readLine()) != null) {
            
        if (line != null) {
            int footerCount = Integer.parseInt(line.trim());
                System.out.println("Footer Count is: "+ footerCount);
            } 
        }
    } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}


