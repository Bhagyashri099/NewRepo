package RunnerClasss;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features= {"@target/failed_scenarios.txt"},
glue= {"steps", "TestSetup"},
	    // Cucumber picks the failed scenarios from this file
	    plugin = {"pretty", "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "html:target/rerun-reports.html"} // Generate separate reports for the rerun
	)
public class FailedTestRunner extends AbstractTestNGCucumberTests{
	
}

