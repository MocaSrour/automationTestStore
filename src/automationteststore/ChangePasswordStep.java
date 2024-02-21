package automationteststore;


import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ChangePasswordStep extends Parameters {
	@Test(priority = 6)
	@Given("Navigate to change password page")
	public void navigate_to_change_password_page() {
		driver.navigate().to("https://automationteststore.com/index.php?rt=account/password");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}
	@Test(priority = 7)
	@When("user input Current Password, New Password & press Continue")
	public void user_input_current_password_new_password_press_continue() throws InterruptedException {
		String newPassword = generateRandomString(8);
		Thread.sleep(2000);
		driver.findElement(By.id("PasswordFrm_current_password")).sendKeys(pass);
		driver.findElement(By.id("PasswordFrm_password")).sendKeys(newPassword);
		driver.findElement(By.id("PasswordFrm_confirm")).sendKeys(newPassword);
		
		driver.findElement(By.xpath("//button[@title='Continue']")).click();
	}
	@Test(priority = 8)
	@Then("Password should be changed")
	public void password_should_be_changed() {
		String changePassMsg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		assertEquals(changePassMsg.contains("Success: Your password has been successfully updated."), true);
	}
}
