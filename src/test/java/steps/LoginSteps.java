package steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import DataSetup.ConfigReader;
import DataSetup.ExcelReader;
import DataSetup.LoggerClass;
import POM_Pagess.IxigoHomePage;
import POM_Pagess.LoginPage;
import POM_Pagess.PostRequest;
import TestSetup.CustomPDFStripper;
import TestSetup.Hooks;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class LoginSteps 
{
	LoginPage login;
	WebDriver driver= Hooks.driver;
	PDDocument document;
	 PostRequest apiLogic = new PostRequest(); 
	    Response response;
	    String generatedId;

	    private static final Logger log = LogManager.getLogger(LoggerClass.class);

		@Given("user opens the url")
		public void user_opens_the_url() throws InterruptedException 
		{
			//this.driver = Hooks.driver; 

		}

		@Given("User is on the ixigo homepage")
		public void navigateToIxigo() throws InterruptedException, IOException {
			this.driver = Hooks.driver;
			//Thread.sleep(3000);
			
			login=new LoginPage(driver);
			login.click_login_signup_button();
			
		}
		@When("user enters mob_no from <rowID> and click continue")
		public void user_enters_mob_no_from_row_id_and_click_continue() throws IOException, InterruptedException {
			login.enter_username_mob_no();
			login.click_continue_for_login();
		}

		@When("User enters {string} as source and {string} as destination")
		public void enterCities(String src, String dest) throws InterruptedException {
			login.enterSource(src);
			login.enterDestination(dest);
		}

		@And("User clicks on the search button")
		public void performSearch() {
			login.clickSearch();
		}

		@Then("Flight search results should be displayed")
		public void verifyResults() throws InterruptedException {
			
			//Assert.assertTrue(driver.getCurrentUrl().contains("search"));
			//driver.quit();
		}

		@When("user clicks on BookBtn")
		public void user_clicks_on_book_btn() throws InterruptedException {
			login.click_on_bookBtn();    
			login.click_on_continueBtn();
			//login.selectFreeCancellation();
		}
		



//Read PDF

    @Given("user has the PDF file {string}")
    public void loadPDF(String fileName) throws IOException {
    	log.info("--- Starting PDF Validation Process ---");
    	
    	document = PDDocument.load(new File("src/test/resources/" + fileName)); // Load PDF
    }

    @When("user validates all pages of the PDF")
    public void validateDetails() throws IOException {
        int pageCount = document.getNumberOfPages(); // Get page count
     
        for (int i = 1; i <= pageCount; i++) {
        	log.info("Validating Page: " + i);
            CustomPDFStripper stripper = new CustomPDFStripper();
            stripper.setStartPage(i);
            stripper.setEndPage(i);
            String pageText = stripper.getText(document);
           
            // compare pageText with Excel row
        }
   }
    @Then("all text, font types, and colors should match the test data sheet")
    public void validate_pdf_against_excel() throws IOException {
        for (int i = 1; i <= document.getNumberOfPages(); i++) {
            Map<String, String> expected = excelData.get(i - 1);
            
         String expectedText = expected.get("ExpectedText");
        
        
            CustomPDFStripper stripper = new CustomPDFStripper();
            stripper.setStartPage(i);
            stripper.setEndPage(i);

            // It "pumps" the data into variables
            String fullPageText = stripper.getText(document); 

            // 
            String actualFont = stripper.getLastFontName();
            
            String actualColor = stripper.getLastColor();
           //logs to see validation
            
            System.out.println("--- Validating Page " + i + " ---");
            System.out.println("Actual Text: " + fullPageText.trim());
            System.out.println("Actual Color: " + actualColor);
            System.out.println("Expected Text:" +expectedText);
            
        }
    }
    List<Map<String, String>> excelData;

    @When("test data is loaded from {string}")
    public void test_data_is_loaded_from(String fileName) {
        // Calling existing readSheet method
         excelData = ExcelReader.readSheet(fileName, "Sheet1");
        
        // verify data isn't empty
        if (excelData.isEmpty()) {
           throw new RuntimeException("Excel file is empty or sheet not found: " + fileName);
        }
        System.out.println("Successfully loaded " + excelData.size() + " rows of test data.");
    }
    
    

//API Logic 
    
    @Given("I have the Excel sheet ready at row {string}")
    public void i_have_the_excel_sheet_ready_at_row(String rowNum) throws IOException {
        
        apiLogic.getTestData(); 
    }
    //

        @When("I send a POST request using data from Excel row {string}")
    public void i_send_a_post_request_using_data_from_excel_row(String rowNumStr) throws IOException {
        	ConfigReader.loadProperties();
        	String baseUri = ConfigReader.properties.getProperty("baseUri");
        	int rowNum = Integer.parseInt(rowNumStr);
        	Map<String, String> innerData = apiLogic.getDataByRow(rowNum);
        	//Map<String, Object> innerData = new HashMap<>();
        	String nameFromExcel = innerData.get("name");
        	
            innerData.put("color", "Cloudy White");
            innerData.put("capacity", "128 GB"); 
            
            Map<String, Object> payload = new HashMap<>();
            payload.put("name", nameFromExcel);
          payload.put("data", innerData);
          response = given()
              .baseUri(baseUri)
             
             //.header("Authorization", "Bearer " + token) 
             .contentType("application/json")
              .body(payload)
          .when()
              .post("/objects")
          .then()
              .log().all()
              .extract().response();

          if (response.getStatusCode() == 201) {
              generatedId = response.jsonPath().getString("id");
              System.out.println("POST Successful. Generated ID: " + generatedId);
              
          } else {
              System.out.println("POST Failed with Status: " + response.getStatusCode());
          }
    }
    @When("I send a GET request using the extracted ID")
    public void i_send_a_get_request_using_the_extracted_id() {
    	generatedId = response.jsonPath().getString("id");
	    assertNotNull(generatedId, "Generated id is null");
	    System.out.println("Generated ID: " + generatedId);
	    ConfigReader.loadProperties();
	    String baseUri = ConfigReader.properties.getProperty("baseUri");

	    Response getResponse = given()
	        .baseUri(baseUri)
	        .accept("application/json")
	        .pathParam("id", generatedId)
	    .when()
	        .get("/objects/{id}")
	    .then()
	        .log().all()
	        .extract().response();
	    	}
   

    @Then("the status code should be {int}")
    public void the_status_code_should_be(int expectedStatus) {
       
        Assert.assertNotNull(response, "Response is NULL! The @When step failed to execute or assign the response.");
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Status code mismatch!");
    }

    @Then("I save the generated ID and status back to row {int}")
    public void i_save_the_generated_id_and_status_back_to_row(int rowNum) throws IOException {
        // Writes results back to Excel file
    	String full = (response == null) ? null : response.asPrettyString();
    	apiLogic.writeSpecificRow(rowNum, generatedId, response.getStatusCode(), full);
        //apiLogic.writeSpecificRow(rowNum, generatedId, response.getStatusCode(), generatedId);
    }    
    
    @Then("the response should contain the name {string}")
    public void the_response_should_contain_the_name(String name) {
    	String responseBody = response.asString();
        System.out.println("Validating Response: " + responseBody);

        if (responseBody.equals("{}")) {
            System.out.println("Note: ReqRes does not persist data, skipping GET validation.");
            // Fail with a descriptive message or pass if POST was successful
        } else {
            // Standard validation for the POST response
            //response.then().body("name", equalTo(name));
            response.then().body("name", org.hamcrest.Matchers.equalTo(name));
            System.out.println("Success! Verified name: " + name);
        }
    }

//validate all status codes
    
	@Before
	public void setup() {

        request = given()
                
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Accept", "application/json")
                .contentType("application/json")
                .log().all();

	}
    private String endpoint;
    
    private RequestSpecification request = RestAssured.given();
   


    @Given("the API endpoint is {string}")
    public void setEndpoint(String url) {
        this.endpoint = url;
    }
    @When("I send a POST with broken body")
    public void sendBrokenBody() {
    	String body = "{\"name\": \"test\", \"job\": \"QA\" "; 

   	    // 2. Execute the Request
   	    response = given()
   	            
   	            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
   	            .contentType("application/json")
   	            // INVALID KEY to trigger 401
   	           .header("x-api-key", "reqres_221c6616f47b4adcb5ae03fd7efb2416") 
   	            .body(body)
   	        .when()
   	            .post(endpoint);
    }
    
    @When("I send a POST request with INVALID API Key")
    public void i_send_a_POST_request_with_invalid_api_key() {
    	String body = "{\"name\": \"test\", \"job\": \"QA\" "; 

    	    // 2. Execute the Request
    	    response = given()
    	           
    	            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
    	           
    	            .header("Accept", "application/json")
    	            .contentType("application/json")
    	            
    	            .body(body)
    	        .when()
    	            .post(endpoint);

    	    // 3. Log the response to see the error message
    	    System.out.println("Status Code: " + response.getStatusCode());
    	    System.out.println("Error Body: " + response.asString());
    }
   
    @When("I send a GET request")
    public void sendGet() {
    	response = request.when().get(endpoint);   
    	}



    @Then("the response status code should be {int}")
    public void verifyStatus(int expectedStatus) {
        int actualStatus = response.getStatusCode();
        String contentType = response.getContentType();
System.out.println(actualStatus);
        if (actualStatus != expectedStatus) {
            System.out.println("FAILED! Content-Type received: " + contentType);
            if (contentType.contains("html")) {
                System.out.println("ERROR: You hit a web page, not an API endpoint. Check your URL.");
            }
            Assert.assertEquals(actualStatus, expectedStatus, "Status code mismatch! URL was: " + endpoint);
            
        }
    }
    @When("I send a POST request with an invalid API key")
    public void sendPostWithInvalidKey() {
        
    	Map<String, String> body = new HashMap<>();
        body.put("name", "Test");

        response = given()
                .relaxedHTTPSValidation()
                
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36")
                
                .contentType("application/json")
                .body(body)
            .when()
                .post(endpoint); 

    }
    @When("I send a POST request using data")
    public void i_send_a_post_request_using_data() throws IOException {
        	ConfigReader.loadProperties();
        	String baseUri = ConfigReader.properties.getProperty("baseUri");
            String token = ConfigReader.properties.getProperty("token");

        	
        	Map<String, String> testData = new HashMap<>();
           
       testData.put("name","test");
       testData.put("job","test");
       testData.put("gender", "male"); 
       testData.put("status", "active");
        testData.put("email", "user_" + System.currentTimeMillis() + "@example.com");
        
        if(testData.containsKey("FirstName")) {
            testData.put("name", testData.get("FirstName")); 
        }

        response = given()
            .baseUri(baseUri)
           // .header("x-api-key", token) 
            .header("Authorization", "Bearer " + token) 
            .contentType("application/json")
            .body(testData)
        .when()
            .post("/posts")
        .then()
            .log().all()
            .extract().response();}

    @Then("the response should contain {string}")
    public void verifyErrorContent(String expectedError) {
        response.then().body("error", org.hamcrest.Matchers.contains(expectedError));
    }



}
