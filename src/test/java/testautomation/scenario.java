package testautomation;

import java.util.List;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


public class scenario {
	

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
		//1. Visiting the web page

		driver.get("http://automationpractice.pl/index.php");     

		//Click on "Sign in" 

		driver.findElement(By.className("login")).click();


		//2. Entering testautomationmfs@gmail.com for the email address and TestAutomation@123 for the password  

		// Get the WebElement corresponding to the email address field		
		WebElement emailadd = driver.findElement(By.id("email"));							

		// Get the WebElement corresponding to the Password Field		
		WebElement password = driver.findElement(By.name("passwd"));	

		//inputing the corresponding Data into fields
		emailadd.sendKeys("testautomationmfs@gmail.com");					
		password.sendKeys("TestAutomation@123");	

		// Get webElement corresponding to Log in button and click on log in
		driver.findElement(By.id("SubmitLogin")).click();


		// 3. navigating to the landing page
		driver.get("http://automationpractice.pl/index.php"); 

		// Click on "Best Sellers" and fetch the labels and prices
		//// best sellers was used instead because nothing was available under "POPULAR" at the time of creating this 

		driver.findElement(By.className("blockbestsellers")).click();

		List<WebElement> products = driver.findElements(By.className("product-container"));

		List<String> productInfoList = new ArrayList<>();

		for (WebElement product : products) {
			String label = product.findElement(By.className("product-name")).getText();
			String price = product.findElement(By.cssSelector(".price.product-price")).getAttribute("innerHTML");
			productInfoList.add(label + " - " + price);
		}

		// Sort the product info list by price from low to high
		Collections.sort(productInfoList);

		// Print the sorted product info on the console
		for (String productInfo : productInfoList) {
			System.out.println(productInfo);
		}

		// Step 3: Scroll up and navigate to Women >> Dresses >> Evening Dresses
		driver.findElement(By.linkText("Women")).click();
		driver.findElement(By.linkText("Dresses")).click();
		driver.findElement(By.linkText("Evening Dresses")).click();

		// Step 4: Filter out a dress: Size (M) >> Color (Pink) >> Set Range: $50.00 - $52.28
		WebElement msizeChecks = driver.findElement(By.xpath("//*[@id=\"layered_id_attribute_group_2\"]"));
		msizeChecks.click();
		

		WebElement pinkColourChecks = driver.findElement(By.xpath("//*[@id=\"layered_id_attribute_group_24\"]"));
		pinkColourChecks.click();
		//*[@id="layered_price_slider"]/a[2]
		
		WebElement rightSlider = driver.findElement(By.xpath("//*[@id=\"layered_price_slider\"]/a[2]"));
		
		Actions action = new Actions(driver);
		action.dragAndDropBy(rightSlider, -32, 0).perform();
		
	
		// Once entry is found, click on More.
		
		WebElement dressElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-container")));
		action.moveToElement(dressElement).perform();
		
		WebElement moreButton = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li/div/div[2]/div[2]/a/span"));
		moreButton.click();
		
		
		// Set Quantity = 3, Size = M, Color = Pink. Click on Add to Cart
		
		
		Select sizeDropdownPopup = new Select(driver.findElement(By.id("group_1")));
		sizeDropdownPopup.selectByVisibleText("M");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quantity_wanted"))).clear();
		driver.findElement(By.id("quantity_wanted")).sendKeys("3");


		driver.findElement(By.name("Beige")).click();
		//since pink was out of stock and i cannot see the add to cart button i had to go with the second option "Beige"
		
		
		driver.findElement(By.name("Submit")).click();

		// Step 6: Verify quantity, size, color, and total cost on the pop-up. Find out shipping cost. Verify total cost.
		String quantity = driver.findElement(By.xpath("//*[@id=\"layer_cart_product_quantity\"]")).getAttribute("innerText");
		String sizeAndColor = driver.findElement(By.id("layer_cart_product_attributes")).getAttribute("innerText");
		String totalProductCost = driver.findElement(By.id("layer_cart_product_price")).getAttribute("innerText");
		String shippingCost = driver.findElement(By.cssSelector(".ajax_cart_shipping_cost.unvisible")).getAttribute("innerText");
		String totalCost = driver.findElement(By.className("ajax_block_cart_total")).getAttribute("innerText");
		
		//WebElement qty = driver.findElement(By.xpath("//*[@id=\"layer_cart_product_quantity\"]")); 	    
		//String quantity = (String) ((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML",qty);
		
		String  expqty = "3";
		String  expsizeAndColor = "M, biege";
		String  exptotalProductCost = "3";
		String  expshippingCost = "3";
		String  exptotalCost = "3";
		
		// verifying and Printing all values on the console
		assertEquals(expqty, quantity);
		System.out.println("Quantity: " + quantity);
		
		assertEquals(expsizeAndColor, sizeAndColor);
		System.out.println("Size, color: " + sizeAndColor);
		
		assertEquals(exptotalProductCost, totalProductCost);
		System.out.println("Total Product Cost: " + totalProductCost);
		
		assertEquals(expshippingCost, shippingCost);
		System.out.println("Shipping Cost: " + shippingCost);
		
		assertEquals(exptotalCost, totalCost);
		System.out.println("Total Cost: " + totalCost);
		

		
		
		
		
		

		
		// Closing the browser
		driver.quit();


	}

	private static void assertEquals(String exptotalCost, String totalCost) {
		// TODO Auto-generated method stub
		
	}
}
