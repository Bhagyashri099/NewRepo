package POM_Pagess;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import DataSetup.ConfigReader;
import DataSetup.ExcelReader;
import TestSetup.Hooks;
import jdk.internal.org.jline.utils.Log;

public class LoginPage 
{
	WebDriver driver;
	WebDriverWait wait;
	

	// Locators
	private final By LoginBtn= By.xpath("//button[contains(text(), 'Log in')]");
	private final By MobNo=By.xpath("//input[@placeholder='Enter Mobile Number']");
	private final By ContinueBtn= By.xpath("//button[contains(text(), 'Continue')]");

	private final By fromField= By.xpath("//p[@data-testid='originId']//span[text()='From']");
	private final By toField= By.xpath("//p[@data-testid='destinationId']//span[text()='To']");
	private final By searchBtn = By.xpath("//button[contains(.,'Search')]");
	private final By fromsearchbox=By.xpath("//input[@class='outline-none w-full bg-transparent placeholder:text-disabled pt-3 focus:caret-selection text-primary placeholder:opacity-0 focus:placeholder:opacity-100 font-medium text-lg !pt-5']");
	private final By destsearchbox=By.xpath("(//div[.//label[normalize-space()='To']]//input)[2]");
	private final By BookBtn=By.xpath("(//button[contains(text(), 'Book')])[1]");
	private final By ContinueForPayment=By.xpath("//button[contains(text(), 'Continue')]");
	private final By ContinueToPay=By.xpath("//button[contains(text(), 'Continue To Pay')]");
	private final By Insurance =By.xpath("//button[@role='tab']//span[contains(text(), 'Insurance')]");
	private final By ConfirmBtn =By.xpath("//button[contains(text(),'Confirm')]");



	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
	}


	public void click_login_signup_button() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));


		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(LoginBtn)));
		//driver.findElement(LoginBtn).click();

	}
	public void enter_username_mob_no() throws IOException, InterruptedException
	{
		List<Map<String, String>> cols = ExcelReader.readSheet("TestData/TestData.xlsx", "Sheet1");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		//wait.until(ExpectedConditions.visibilityOfElementLocated(MobNo));
		//wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(MobNo)));
		for (Map<String, String> col : cols) {
			String mob_no = col.get("mob_no");
			//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			System.out.println(mob_no);
			System.out.println(mob_no);
			//driver.findElement(MobNo).sendKeys(mob_no);
		}
	}

	public void click_continue_for_login() throws InterruptedException
	{
		//wait.until(ExpectedConditions.elementToBeClickable(ContinueBtn));
		//driver.findElement(ContinueBtn).click();
		
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class, 'fixed top-0')]")));
	}



	public void enterSource(String source) throws InterruptedException {
		Actions actions = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(fromField));
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(fromField));
		el.click();

		driver.findElement(fromsearchbox).sendKeys(source);
		try {
		    new WebDriverWait(driver, Duration.ofSeconds(5))
		        .until(ExpectedConditions.presenceOfElementLocated(By.id("non-existent-id")));
		} catch (TimeoutException e) {
		    // Continue after 5 seconds
		}
		//actions.pause(Duration.ofSeconds(5));
		//Thread.sleep(3000);

		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class,'Autocompleter_animate__')]//div[@role='listitem'])[1]"))).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		actions.pause(Duration.ofSeconds(5));
		//Thread.sleep(2000);
	}

	public void enterDestination(String dest) throws InterruptedException {
		//WebElement el = driver.findElement(toField);
		driver.findElement(destsearchbox).sendKeys(dest);
		Thread.sleep(5000);

		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'result-item')]//div[text()='"+dest+"']"))).click();
		driver.findElement(By.xpath("(//div[@role='listitem'][contains(.,'"+ dest +"')])[3]")).click();
	}

	public void clickSearch() {
		driver.findElement(searchBtn).click();
		Log.info("print logs: clicking on search button");
	}


	public void click_on_bookBtn() throws InterruptedException
	{

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollBy(0, 350)"); 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.elementToBeClickable(BookBtn));
		driver.findElement(BookBtn).click();
	}


	public void click_on_continueBtn()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.elementToBeClickable(ContinueForPayment));
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.findElement(ContinueForPayment).click();

	}
	public void selectFreeCancellation() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("window.scrollBy(800, 900)"); 
		Log.info("selecting free cancellation");;
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		WebElement radioBtn = driver.findElement(By.id("Free Cancellation-radio"));
		js.executeScript("arguments[0].click();", radioBtn);


		WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox'][1]"));

		// Create the JavascriptExecutor instance

		// Perform the click directly in the browser engine (ignores the footer overlay)
		js.executeScript("arguments[0].click();", checkbox);

		//js.executeScript("window.scrollBy(300, 300)"); 
		//driver.findElement(By.xpath("//input[@type='checkbox'][1]")).click();
		try {
		    new WebDriverWait(driver, Duration.ofSeconds(5))
		        .until(ExpectedConditions.presenceOfElementLocated(By.id("non-existent-id")));
		} catch (TimeoutException e) {
		    // Continue after 5 seconds
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(ConfirmBtn));
		driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
		//Thread.sleep(3000);
		driver.findElement(ContinueForPayment).click();
		Thread.sleep(10000);





		try {
			// 2. Use findElements to check visibility without crashing if missing
			List<WebElement> buttons = driver.findElements(ContinueForPayment);

			if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
				
				js.executeScript("arguments[0].click();", buttons.get(0));

				
				wait.until(ExpectedConditions.elementToBeClickable(ContinueToPay)).click();
			} 
			else {
				
				js.executeScript("window.scrollTo(0, 0);");

				wait.until(ExpectedConditions.elementToBeClickable(Insurance)).click();

				
				WebElement payBtn = wait.until(ExpectedConditions.elementToBeClickable(ContinueForPayment));
				js.executeScript("arguments[0].click();", payBtn);

				wait.until(ExpectedConditions.elementToBeClickable(ContinueToPay)).click();
			}

		
			wait.until(ExpectedConditions.urlContains("payments"));
			Assert.assertTrue(driver.getCurrentUrl().contains("payments"), "Failed to reach Payment page!");

		} catch (Exception e) {
			System.out.println("Flow failed at: " + e.getMessage());
			Assert.fail("Flight booking flow interrupted.");
		}}}



	


