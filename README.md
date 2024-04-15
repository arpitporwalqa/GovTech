# GovTech
Automation Challenge for GovTech

This automation project is implemented using Java, Selenium WebDriver, Serenity, and Page Object Model (POM) design pattern. It contains test cases for various user stories and utilizes reusable methods for efficient test automation.

Prerequisites
Ensure that Java version 15 or higher is installed on your system.

Project Structure

GovTechChallenge
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── (main Java packages)
│   │   ├── resources/
│   │   │   ├── (main resources)
│   ├── test/
│   │   ├── java/
│   │   │   ├── steps/
│   │   │   │   ├── USSteps.java
│   │   │   ├── testcases/
│   │   │   │   ├── BaseTest.java
│   │   │   │   ├── UserStory1Test.java
│   │   │   │   ├── UserStory2Test.java
│   │   │   │   ├── UserStory3Test.java
│   │   │   │   ├── UserStory4Test.java
│   │   │   │   ├── UserStory5Test.java
│   │   │   │   ├── UserStory6Test.java
│   │   ├── resources/
│   │   │   ├── (test resources)
├── target/
├── invalid.csv
├── valid.csv
├── pom.xml


Test Cases
User Story 1: Contains test cases related to the creation of a single working class hero via API call.
User Story 2: Contains test cases related to uploading a CSV file to create multiple heroes.
User Story 3: Contains test cases related to generating a tax relief egress file from the UI.
User Story 4: Contains test cases related to creating a working class hero with vouchers via API call.
User Story 5: Contains test cases related to checking if a working class hero owes money via API call.
User Story 6: Contains test cases related to providing an API that gives insight into the number of vouchers each customer has for each voucher category.

Basic Dependencies
Serenity BDD (4.1.0): Serenity is used for test automation and reporting.
Serenity REST Assured (4.1.6): Serenity REST Assured integration for performing API testing.
JUnit (provided by Serenity): JUnit is used as the test framework.


How to Run the Tests:
Prerequisites:
- The application must be already setup and running on the local machine.
- Database must be already setup and running on the local machine.

Execution Steps:
- Clone the project repository to your local machine.
- Ensure that Java 15 or higher is installed on your system.
- Navigate to the project directory in the terminal.
- Execute the following Maven command to run the tests:
mvn clean verify
- After the tests have completed, the Serenity HTML reports can be found in the target/site/serenity directory. Open the index.html file from browser to view the reports.
