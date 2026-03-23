package RunnerClasss;

import org.testng.annotations.AfterSuite;

import TestSetup.EmailUtility;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features= {"src/test/resources/features"},
glue= {"steps", "TestSetup"},
plugin= {"pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", 
		"rerun:target/failed_scenarios.txt" },
tags = " not @ignore"
)

 public class TestRunner extends AbstractTestNGCucumberTests{

	@AfterSuite
    public void sendEmail() {
        
        EmailUtility.sendReportAfterExecution("test output\\PdfReport\\ExtentPdf.pdf");
    }

}


