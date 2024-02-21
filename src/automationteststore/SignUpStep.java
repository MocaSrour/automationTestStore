package automationteststore;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignUpStep extends Parameters {
	@Test(priority = 1)
	@Given("browser is open")
	public void browser_is_open() {
		driver.get("https://automationteststore.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}
	@Test(priority = 2)
	@And("user is on sign up page")
	public void user_is_on_sign_up_page() {
		WebElement signUpBtn = driver.findElement(By.id("customer_menu_top")).findElement(By.tagName("li"));
		signUpBtn.click();
		WebElement continueBtn = driver.findElement(By.xpath("//button[normalize-space()='Continue']"));
		continueBtn.click();
	}
	@Test(priority = 3)
	@When("user enters all feilds")
	public void user_enters_all_feilds() throws InterruptedException {
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test(priority = 4)
	@And("user clicks sign up button or continue")
	public void user_clicks_sign_up_button_or_continue() {
		WebElement continueSignUpBtn = driver.findElement(By.xpath("//button[normalize-space()='Continue']"));
		continueSignUpBtn.click();

	}
	@Test(priority = 5)
	@Then("user is navigated to home page")
	public void user_is_navigated_to_home_page() {
		assertEquals(driver.findElement(By.tagName("h1")).getText(), "YOUR ACCOUNT HAS BEEN CREATED!");
	}
}