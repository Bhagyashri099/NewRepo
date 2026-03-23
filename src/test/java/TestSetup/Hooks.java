package TestSetup;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import DataSetup.ConfigReader;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks
{
	public static WebDriver driver;
	ConfigReader Authconfig = new ConfigReader();
	public static ExtentReports extent;
    public static ExtentSparkReporter spark;
    
	@BeforeSuite
    public void setupReport() {
        // 2. Initialize the reporter and the extent object
        spark = new ExtentSparkReporter("test output\\PdfReport\\ExtentPdf.pdf");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }
	
	@Before("not @skip_setup1")
	public void setUp(Scenario scenario) throws MalformedURLException {
		String env = Authconfig.getExecutionEnv().trim();
		String hubUrl = Authconfig.getRemoteUrl();
		String browser = Authconfig.getBrowser();
		boolean isHeadless = Authconfig.getHeadless() || scenario.getSourceTagNames().contains("@headless");
		ChromeOptions options = new ChromeOptions();
		if (isHeadless) {
	        options.addArguments("--headless");
	        options.addArguments("--window-size=1920,1080");
	        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36");
	    }
		if (env.equalsIgnoreCase("remote")) {
			
			options.setCapability("browserName", browser);

			driver = new RemoteWebDriver(new URL(hubUrl), options);


			// This line executes the script on the remote hub
			//driver = new RemoteWebDriver(new URL(hubUrl), caps);
		} 
		else 

		{
			// This block executes for local testing
			driver = new ChromeDriver(options); 
			//System.out.println("local");

		}

			        driver.manage().window().maximize();
			        driver.get(Authconfig.getUrl());
	}   

	@After
	public void tearDown(Scenario scenario) {
	    if (scenario.isFailed()) {
	        // This attaches the screenshot directly to your Extent Report
	        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	        scenario.attach(screenshot, "image/png", "Failed_Screenshot");
	    }
	}
	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}}
	@AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            
            extent.flush(); 
        }
        EmailUtility.sendReportAfterExecution("test output\\PdfReport\\ExtentPdf.pdf");
    }
	}
	
	


	


