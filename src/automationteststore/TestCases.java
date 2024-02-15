package automationteststore;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

public class TestCases extends Parameters {

	private String loginName;
	private String pass;

	@BeforeTest
	public void Setup() {
		driver.get("https://automationteststore.com/");
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void SignUp() throws InterruptedException {
		WebElement signUpBtn = driver.findElement(By.id("customer_menu_top")).findElement(By.tagName("li"));
		signUpBtn.click();
		WebElement continueBtn = driver.findElement(By.xpath("//button[normalize-space()='Continue']"));
		continueBtn.click();

		// Personal Details
		WebElement firstNameInput = driver.findElement(By.id("AccountFrm_firstname"));
		WebElement lastNameInput = driver.findElement(By.id("AccountFrm_lastname"));
		WebElement emailInput = driver.findElement(By.id("AccountFrm_email"));
		WebElement phoneInput = driver.findElement(By.id("AccountFrm_telephone"));
		WebElement faxInput = driver.findElement(By.id("AccountFrm_fax"));

		// Address
		WebElement companyInput = driver.findElement(By.id("AccountFrm_company"));
		WebElement addressFirstInput = driver.findElement(By.id("AccountFrm_address_1"));
		WebElement addressSecondInput = driver.findElement(By.id("AccountFrm_address_2"));
		WebElement cityInput = driver.findElement(By.id("AccountFrm_city"));
		WebElement regionInput = driver.findElement(By.id("AccountFrm_zone_id"));
		WebElement ZIPCodeInput = driver.findElement(By.id("AccountFrm_postcode"));
		WebElement countryInput = driver.findElement(By.id("AccountFrm_country_id"));

		// Login Details
		WebElement loginNameInput = driver.findElement(By.id("AccountFrm_loginname"));
		WebElement passInput = driver.findElement(By.id("AccountFrm_password"));
		WebElement passConfirmInput = driver.findElement(By.id("AccountFrm_confirm"));

		// Newsletter
		WebElement[] newsletter = { driver.findElement(By.id("AccountFrm_newsletter0")),
				driver.findElement(By.id("AccountFrm_newsletter1")) };

		// Agreement Policy
		WebElement checkAgreement = driver.findElement(By.id("AccountFrm_agree"));

		WebElement continueSignUpBtn = driver.findElement(By.xpath("//button[normalize-space()='Continue']"));

		try {

			JsonObject user = getRandomUser();

			// Random Personal Details
			String firstName = user.get("name").getAsJsonObject().get("first").getAsString();
			String lastName = user.get("name").getAsJsonObject().get("last").getAsString();
			String email = user.get("email").getAsString();
			String phone = user.get("phone").getAsString();
			String fax = user.get("location").getAsJsonObject().get("postcode").getAsString();

			// Address
			String company = user.get("name").getAsJsonObject().get("last").getAsString() + " "
					+ user.get("location").getAsJsonObject().get("city").getAsString();
			String addressFirst = user.get("location").getAsJsonObject().get("street").getAsJsonObject().get("number")
					.getAsString();
			String addressSecond = user.get("location").getAsJsonObject().get("street").getAsJsonObject().get("name")
					.getAsString();
			String city = user.get("location").getAsJsonObject().get("city").getAsString();
			String ZIPCode = user.get("location").getAsJsonObject().get("postcode").getAsString();
			String country = user.get("location").getAsJsonObject().get("country").getAsString();

			// Login Details
			loginName = user.get("login").getAsJsonObject().get("username").getAsString();
			pass = user.get("login").getAsJsonObject().get("salt").getAsString();

			// Fill in sign-up form
			firstNameInput.sendKeys(firstName);
			lastNameInput.sendKeys(lastName);
			emailInput.sendKeys(email);
			phoneInput.sendKeys(phone);
			faxInput.sendKeys(fax);

			//
			companyInput.sendKeys(company);
			addressFirstInput.sendKeys(addressFirst);
			addressSecondInput.sendKeys(addressSecond);
			cityInput.sendKeys(city);
			ZIPCodeInput.sendKeys(ZIPCode);
			countryInput.sendKeys(country);
			Thread.sleep(2000);
			Select regionSelector = new Select(regionInput);
			Random rndRegion = new Random();
			int regionSize = regionSelector.getOptions().size();
			int rndRegionNext = rndRegion.nextInt(regionSize) + 1;
			regionSelector.selectByIndex(rndRegionNext);

			//
			loginNameInput.sendKeys(loginName);
			passInput.sendKeys(pass);
			passConfirmInput.sendKeys(pass);

			//
			Random rndNewsletter = new Random();
			newsletter[rndNewsletter.nextInt(2)].click();
			checkAgreement.click();
			continueSignUpBtn.click();

			Thread.sleep(2000);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			assertEquals(driver.findElement(By.tagName("h1")).getText(), "YOUR ACCOUNT HAS BEEN CREATED!");
		}
	}

	@Test(priority = 2, enabled = true)
	public void Logout() {
		String logOutURL = "https://automationteststore.com/index.php?rt=account/logout";
		driver.get(logOutURL);
		assertEquals(driver.getCurrentUrl(), logOutURL);
	}

	@Test(priority = 3, enabled = true)
	public void Login() {
		driver.get("https://automationteststore.com/index.php?rt=account/login");

		WebElement nameInput = driver.findElement(By.id("loginFrm_loginname"));
		WebElement passInput = driver.findElement(By.id("loginFrm_password"));

		nameInput.sendKeys(loginName);
		passInput.sendKeys(pass);

		driver.findElement(By.id("loginFrm")).findElement(By.xpath("//button[normalize-space()='Login']")).click();

		String urlSignedIn = "https://automationteststore.com/index.php?rt=account/account";
		assertEquals(driver.getCurrentUrl(), urlSignedIn);
	}

	@Test(priority = 4, enabled = false)
	public void AddSkipConditioner() throws InterruptedException {
		driver.get("https://automationteststore.com/index.php?rt=product/category&path=52_54");
		List<WebElement> conditionerList = driver.findElement(By.cssSelector(".thumbnails.grid.row.list-inline"))
				.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12"));

		for (int i = 0; i < conditionerList.size(); i += 2) {
			conditionerList = driver.findElement(By.cssSelector(".thumbnails.grid.row.list-inline"))
					.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12"));
			conditionerList.get(i).findElement(By.className("productcart")).click();
			if (driver.getCurrentUrl().contains("product_id")) {
				driver.findElement(By.id("product")).findElement(By.tagName("a")).click();
				driver.get("https://automationteststore.com/index.php?rt=product/category&path=52_54");
			}
		}
		int itemsNumCart = Integer.parseInt(driver.findElement(By.xpath("//ul[@class='nav topcart pull-left']"))
				.findElement(By.tagName("span")).getText());

		assertEquals(itemsNumCart, (conditionerList.size() + 1) / 2);
	}

	@AfterTest
	public void Post() throws InterruptedException {
//		Thread.sleep(20000);
//		driver.close();
	}
}
