package Assignment;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Assignment2 {

	public static void main(String[] args) throws InterruptedException {

		/*
		 *  Write webdriver script for the following flow 
		 *		1. login --> pay bill --> add new payee --> and log out
		 *		2. Feedback
		 *	use all the locator techniques in the script at least once 
		 */

		WebDriver driver;

		// Launch browser and navigate to the home page
		driver = setup();
		
		WebElement searchBox_txtBox = driver.findElement(By.className("search-query"));
		searchBox_txtBox.sendKeys("test search");
		
		WebElement sigIn_btn = driver.findElement(By.tagName("button"));
		try {
			sigIn_btn.click();
		} catch (Exception e1) {
			
			driver.findElement(By.id("signin_button")).click();
		}

		WebElement forgotPassword_link = driver.findElement(By.partialLinkText("Forgot"));
		forgotPassword_link.click();

		try {
			assertEquals(driver.getTitle(), "Zero - Forgotten Password");
		} catch (AssertionError e) {
			e.printStackTrace();
		}
		driver.navigate().back();

		WebElement logIn_txtBox = driver.findElement(By.name("user_login"));
		logIn_txtBox.sendKeys("username");


		WebElement password_txtBox = driver.findElement(By.cssSelector("[type='password']"));
		password_txtBox.sendKeys("password");

		WebElement keepMeSignedIn_checkBox = driver.findElement(By.cssSelector("#user_remember_me"));
		if(!keepMeSignedIn_checkBox.isSelected()) {
			keepMeSignedIn_checkBox.click();
		}

		WebElement signIn_btn = driver.findElement(By.cssSelector("input[name='submit']"));
		signIn_btn.click();
		driver.findElement(By.id("details-button")).click();
		driver.findElement(By.id("proceed-link")).click();

		WebElement payBills_link = driver.findElement(By.linkText("Pay Bills"));
		payBills_link.click();

		WebElement addNewPayee_link = driver.findElement(By.xpath("//a[contains(text(), 'Add New')]"));
		addNewPayee_link.click();
		Thread.sleep(2000);
		WebElement payeeName_textBox = driver.findElement(By.cssSelector("input#np_new_payee_name"));
		payeeName_textBox.sendKeys("souvick");

		WebElement payeesAddress_textArea = driver.findElement(By.cssSelector("textarea.span4"));
		payeesAddress_textArea.sendKeys("test address, 123 road");

		WebElement account_txtBox = driver.findElement(By.cssSelector("input.span4[name='account']"));
		account_txtBox.sendKeys("Savings");

		WebElement payeeDetails_txtBox = driver.findElement(By.xpath("//input[@name='details']"));
		payeeDetails_txtBox.sendKeys("telephone bill");

		WebElement add_btn = driver.findElement(By.xpath("//input[@id='add_new_payee']"));
		add_btn.click();
		
		WebElement alertMessage = driver.findElement(By.id("alert_content"));

		try {
			assertEquals(alertMessage.getText(), "The new payee souvick was successfully created.");
		} catch (AssertionError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebElement userName_drpDwn = driver.findElement(By.xpath("//li[@class='dropdown'][2]"));
		userName_drpDwn.click();

		WebElement logOut_btn = driver.findElement(By.xpath("//a[contains(@href, 'logout')]"));
		logOut_btn.click();
		
		driver.findElement(By.cssSelector("button")).isEnabled();
		
		WebElement feedBack_link = driver.findElement(By.xpath("//li[@id='feedback']"));
		feedBack_link.click();
		
		driver.findElement(By.cssSelector(".search-query")).sendKeys("test search");
		
		WebElement feedBack_YourName_txtBox = driver.findElement(By.cssSelector("input#name[name='name']"));
		feedBack_YourName_txtBox.sendKeys("souvick goswami");
		
		WebElement feedBack_email_txtBox = driver.findElement(By.xpath("//input[@name='email']"));
		feedBack_email_txtBox.sendKeys("test@test.com");
		
		driver.findElement(By.id("subject")).sendKeys("feedback");
			
		WebElement feedBack_comments_txtArea = driver.findElement(By.cssSelector("textarea#comment.span6"));
		
		driver.findElement(By.name("submit")).click();
		// close browser
		tearDown(driver);

	}

	public static WebDriver setup() {


		// set system path for browser driver
		//Note: please change the path
		System.setProperty("webdriver.chrome.driver","C:\\SeleniumBrowserDriver\\chromedriver.exe");

		// open browser
		WebDriver driver = new ChromeDriver();    
		driver.manage().window().maximize();

		//open url
		driver.get("http://zero.webappsecurity.com/");
		return driver;
	}

	public static void tearDown(WebDriver driver) {

		//close browser
		driver.close();

		// kill/quit driver
		driver.quit();


	}

}
