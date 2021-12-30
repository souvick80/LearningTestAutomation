package Assignment;

import static org.testng.Assert.assertEquals;

import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Assignment3 {

	public static void main(String[] args) throws InterruptedException {


		// ##################### Alert Box Assignment ##################

		// Launching the url
		WebDriver driver; 
		driver = setup("http://zero.webappsecurity.com/index.html");

		// logging into the application
		logIn(driver);

		// clicking on the Pay bills menu
		WebElement payBills_link = driver.findElement(By.linkText("Pay Bills"));
		payBills_link.click();
		WebElement purchaseForeignCurrency_link = driver.findElement(By.linkText("Purchase Foreign Currency"));
		purchaseForeignCurrency_link.click();
		Thread.sleep(1000);

		// clicking on the purchase button
		WebElement purchase_btn = driver.findElement(By.xpath("//input[@id='purchase_cash']"));
		purchase_btn.click();

		// Handling the alert
		Alert jsAlert = driver.switchTo().alert();
		String alertMessage = jsAlert.getText();
		try {
			assertEquals(alertMessage,
					"Please, ensure that you have filled all the required fields with valid values.", "Message showing invalid");
		} catch (AssertionError e) {
			e.printStackTrace();
		}

		// Accepting the alert
		jsAlert.accept();

		// Logging out from the application
		logOut(driver);

		// Closing the browser
		tearDown(driver);


		//################## Window Handles ######################

		driver = setup("https://www.naukri.com/");	

		String pWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();

		for(String s: handles) {
			if(!pWindow.equals(s)) {
				WebDriver childWindow = driver.switchTo().window(s);
				childWindow.manage().window().maximize();
				System.out.println("Child window is: " + childWindow.getTitle());
				driver.findElement(By.xpath("//img[contains(@src, 'techmahindra')]")).click();
				childWindow.close();
			}
		}

		driver.switchTo().window(pWindow);

		//		Thread.sleep(10000);
		//		driver.findElement(By.className("desc")).getText();
		//		
		//		System.out.println(driver.findElement(By.xpath("//p[@class='caption']")).getText());

		// Closing the browser
		tearDown(driver);

		// ################ Confirmation Box #############

		driver = setup("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_confirm");
		driver.switchTo().frame(driver.findElement(By.id("iframeResult")));
		driver.findElement(By.xpath("//button[contains(text(), 'Try it')]")).click();
		Alert w3Alert = driver.switchTo().alert();
		System.out.println("Aler message is: "+ w3Alert.getText());
		w3Alert.accept();

		tearDown(driver);



	}

	public static WebDriver setup(String url) {


		// set system path for browser driver
		// Note: please change the path
		System.setProperty("webdriver.chrome.driver","C:\\SeleniumBrowserDriver\\chromedriver.exe");

		// open browser
		WebDriver driver = new ChromeDriver();    
		driver.manage().window().maximize();

		//open url
		driver.get(url);

		return driver;
	}

	public static void tearDown(WebDriver driver) {

		//close browser
		driver.close();

		// kill/quit driver
		driver.quit();


	}

	public static void logIn(WebDriver driver) {

		driver.findElement(By.id("signin_button")).click();
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
		WebElement details_btn = driver.findElement(By.id("details-button"));
		if(details_btn.isDisplayed()) {
			details_btn.click();
			driver.findElement(By.id("proceed-link")).click();
		}


	}

	public static void logOut(WebDriver driver) {

		WebElement userName_drpDwn = driver.findElement(By.xpath("//li[@class='dropdown'][2]"));
		userName_drpDwn.click();

		WebElement logOut_btn = driver.findElement(By.xpath("//a[contains(@href, 'logout')]"));
		logOut_btn.click();
	}

}
