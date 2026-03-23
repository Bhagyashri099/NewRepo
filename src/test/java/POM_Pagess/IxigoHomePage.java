package POM_Pagess;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import DataSetup.ExcelReader;
//import TestSetup.ExcelReader;

public class IxigoHomePage {
//	WebDriver driver;
//	WebDriverWait wait;
//
//	// Locators
//	private final By LoginBtn= By.xpath("//button[contains(text(), 'Log in')]");
//	private final By MobNo=By.xpath("//input[@placeholder='Enter Mobile Number']");
//	private final By ContinueBtn= By.xpath("//button[contains(text(), 'Continue')]");
//
//	private final By fromField= By.xpath("//p[@data-testid='originId']//span[text()='From']");
//	private final By toField= By.xpath("//p[@data-testid='destinationId']//span[text()='To']");
//	By searchBtn = By.xpath("//button[contains(.,'Search')]");
//	private final By fromsearchbox=By.xpath("//input[@class='outline-none w-full bg-transparent placeholder:text-disabled pt-3 focus:caret-selection text-primary placeholder:opacity-0 focus:placeholder:opacity-100 font-medium text-lg !pt-5']");
//	private final By destsearchbox=By.xpath("(//div[.//label[normalize-space()='To']]//input)[2]");
//	private final By BookBtn=By.xpath("(//button[contains(text(), 'Book')])[1]");
//	private final By ContinueForPayment=By.xpath("//button[contains(text(), 'Continue')]");
//	private final By ContinueToPay=By.xpath("//button[contains(text(), 'Continue To Pay')]");
//	private final By Insurance =By.xpath("//button[@role='tab']//span[contains(text(), 'Insurance')]");
//	private final By ConfirmBtn =By.xpath("//button[contains(text(),'Confirm')]");
//
//
//
//	public IxigoHomePage(WebDriver driver) {
//		this.driver = driver;
//		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//	}
//
//
//	public void click_login_signup_button() throws InterruptedException
//	{
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
//
//
//		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(LoginBtn)));
//		driver.findElement(LoginBtn).click();
//
//	}
//	public void enter_username_mob_no() throws IOException, InterruptedException
//	{
//		List<Map<String, String>> cols = ExcelReader.readSheet("TestData/TestData.xlsx", "Sheet1");
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(MobNo));
//		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(MobNo)));
//		for (Map<String, String> col : cols) {
//			String mob_no = col.get("mob_no");
//			//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//			System.out.println(mob_no);
//			driver.findElement(MobNo).sendKeys(mob_no);
//		}
//	}
//
//	public void click_continue_for_login() throws InterruptedException
//	{
//		wait.until(ExpectedConditions.elementToBeClickable(ContinueBtn));
//		driver.findElement(ContinueBtn).click();
//
//		Thread.sleep(20000);
//	}
//
//
//
//
//
//
//	public void enterSource(String source) throws InterruptedException {
//		wait.until(ExpectedConditions.visibilityOfElementLocated(fromField));
//		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(fromField));
//		el.click();
//
//		driver.findElement(fromsearchbox).sendKeys(source);
//		Thread.sleep(3000);
//
//		// Select the first matching suggestion
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class,'Autocompleter_animate__')]//div[@role='listitem'])[1]"))).click();
//		Thread.sleep(2000);
//	}
//
//	public void enterDestination(String dest) throws InterruptedException {
//		WebElement el = driver.findElement(toField);
//		driver.findElement(destsearchbox).sendKeys(dest);
//		Thread.sleep(5000);
//
//		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'result-item')]//div[text()='"+dest+"']"))).click();
//		driver.findElement(By.xpath("(//div[@role='listitem'][contains(.,'"+ dest +"')])[3]")).click();
//	}
//
//	public void clickSearch() {
//		driver.findElement(searchBtn).click();
//	}
//
//
//	public void click_on_bookBtn() throws InterruptedException
//	{
//
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//
//		js.executeScript("window.scrollBy(0, 350)"); 
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//		wait.until(ExpectedConditions.elementToBeClickable(BookBtn));
//		driver.findElement(BookBtn).click();
//	}
//
//
//	public void click_on_continueBtn()
//	{
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//		wait.until(ExpectedConditions.elementToBeClickable(ContinueForPayment));
//		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//		driver.findElement(ContinueForPayment).click();
//
//	}
//	public void selectFreeCancellation() throws InterruptedException
//	{
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		//js.executeScript("window.scrollBy(800, 900)"); 
//
//		WebElement radioBtn = driver.findElement(By.id("Free Cancellation-radio"));
//		js.executeScript("arguments[0].click();", radioBtn);
//
//		//driver.findElement(By.id("Free Cancellation-radio")).click();
//
//		//select the user to book flight for
//		WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox'][1]"));
//
//		// Create the JavascriptExecutor instance
//
//		// Perform the click directly in the browser engine (ignores the footer overlay)
//		js.executeScript("arguments[0].click();", checkbox);
//
//		//js.executeScript("window.scrollBy(300, 300)"); 
//		//driver.findElement(By.xpath("//input[@type='checkbox'][1]")).click();
//		Thread.sleep(3000);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(ConfirmBtn));
//		driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
//		//Thread.sleep(3000);
//		driver.findElement(ContinueForPayment).click();
//		Thread.sleep(10000);
//
//
//
//		//try
//
//
//		try {
//			// 2. Use findElements to check visibility without crashing if missing
//			List<WebElement> buttons = driver.findElements(ContinueForPayment);
//
//			if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
//				// Use JS click to bypass the 'z-5' footer overlay
//				js.executeScript("arguments[0].click();", buttons.get(0));
//
//				// Wait for next button to be clickable instead of sleeping
//				wait.until(ExpectedConditions.elementToBeClickable(ContinueToPay)).click();
//			} 
//			else {
//				// 3. Fallback: Scroll to top and handle Insurance
//				js.executeScript("window.scrollTo(0, 0);");
//
//				wait.until(ExpectedConditions.elementToBeClickable(Insurance)).click();
//
//				// Click the payment button after insurance selection
//				WebElement payBtn = wait.until(ExpectedConditions.elementToBeClickable(ContinueForPayment));
//				js.executeScript("arguments[0].click();", payBtn);
//
//				wait.until(ExpectedConditions.elementToBeClickable(ContinueToPay)).click();
//			}
//
//			// 4. Verification with a wait for the URL change
//			wait.until(ExpectedConditions.urlContains("payments"));
//			Assert.assertTrue(driver.getCurrentUrl().contains("payments"), "Failed to reach Payment page!");
//
//		} catch (Exception e) {
//			System.out.println("Flow failed at: " + e.getMessage());
//			Assert.fail("Flight booking flow interrupted.");
//		}}}


//try



//		WebElement button=driver.findElement(ContinueForPayment);
//		if(button.isDisplayed()) 
//		{
//			button.click();
//		
//			Thread.sleep(5000);
//			driver.findElement(ContinueToPay).click();
//			Thread.sleep(5000);
//			Assert.assertTrue(driver.getCurrentUrl().contains("payments"));
//		}
//		else
//		{
//			js.executeScript("window.scrollTo(0, 0);");
//			
//			driver.findElement(Insurance).click();
//			Thread.sleep(5000);
//			driver.findElement(ContinueForPayment).click();
//			Thread.sleep(5000);
//			driver.findElement(ContinueToPay).click();
//			Thread.sleep(5000);
//			Assert.assertTrue(driver.getCurrentUrl().contains("payments"));
//		}
	}



