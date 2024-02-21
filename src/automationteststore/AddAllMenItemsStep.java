package automationteststore;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAllMenItemsStep extends Parameters {
	List<WebElement> itemsList;
	@Test
	@Given("men section page is open")
	public void men_section_page_is_open() {
		driver.get("https://automationteststore.com/index.php?rt=product/category&path=58");
	}
	@Test
	@When("user add all items")
	public void user_add_all_items() {
		itemsList = driver.findElement(By.cssSelector(".thumbnails.grid.row.list-inline"))
				.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12"));

		for (int i = 0; i < itemsList.size(); i++) {
			itemsList = driver.findElement(By.cssSelector(".thumbnails.grid.row.list-inline"))
					.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12"));
			itemsList.get(i).findElement(By.className("productcart")).click();
			if (driver.getCurrentUrl().contains("product_id")) {
				driver.findElement(By.id("product")).findElement(By.tagName("a")).click();
				driver.get("https://automationteststore.com/index.php?rt=product/category&path=58");
			}
		}
	}
	@Test
	@Then("cart should contain all items")
	public void cart_should_contain_all_items() {
		int itemsNumCart = Integer.parseInt(driver.findElement(By.xpath("//ul[@class='nav topcart pull-left']"))
				.findElement(By.tagName("span")).getText());
		assertEquals(itemsNumCart, itemsList.size());
	}
}
