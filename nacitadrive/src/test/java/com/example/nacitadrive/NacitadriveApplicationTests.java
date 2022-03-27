package com.example.nacitadrive;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.after;

import java.util.concurrent.TimeUnit;
import java.util.*;  
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NacitadriveApplicationTests {
	@Test
	void contextLoads() throws InterruptedException {
		Scanner sc= new Scanner(System.in); 
		WebDriver driver = null;
		System.out.print("Choose browser: 1)Google Chrome  | 2)Firefox | 3)Edge |"); 
		int choice = sc.nextInt(); 
		if(choice == 1) {
			  System.setProperty("webdriver.chrome.driver","E:\\Java Selenium\\nacitadrive\\chromedriver.exe");
			   driver = new ChromeDriver();
		}
		else if(choice == 2)
		{
			 System.setProperty("webdriver.gecko.driver","E:\\Java Selenium\\nacitadrive\\geckodriver.exe");
			   driver = new ChromeDriver();
		}
		else if(choice ==3)
		{
			  System.setProperty("webdriver.edge.driver","E:\\Java Selenium\\nacitadrive\\msedgedriver.exe");
			   driver = new EdgeDriver();
		}
		  
		  driver.get("http://automationpractice.com/");
		  // finding the dress
		  driver.findElement(By.id("search_query_top")).sendKeys("dress");
		  WebElement searchButton =   driver.findElement(By.name("submit_search"));
		  searchButton.click();
		  WebElement selectedTshirt = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[2]/ul/li[6]/div/div[2]/h5/a"));
		  selectedTshirt.click();
		  
		  // Price and Quantity
		  WebElement OrigUnitPrice = driver.findElement(By.xpath("//*[@id=\"our_price_display\"]"));
		  String origPriceText = OrigUnitPrice.getText();
		  origPriceText = origPriceText.replace("$","");
		  double origPrice = Double.parseDouble(origPriceText);
		  System.out.println("Orig price is: " + origPrice);
		  
		  WebElement addQuanityButton = driver.findElement(By.xpath("//*[@id=\"quantity_wanted_p\"]/a[2]/span/i"));
		  addQuanityButton.click(); 
		  
		  // size and color
		  WebElement selectSizeButton = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[4]/div/div/div/div[4]/form/div/div[2]/div/fieldset[1]/div/div/select/option[3]"));
		  selectSizeButton.click();
		  Thread.sleep(2000);
		  WebElement selectColorButton = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[4]/div/div/div/div[4]/form/div/div[2]/div/fieldset[2]/div/ul/li[2]/a"));
		  selectColorButton.click();
		  
		  // add to cart and proceed
		  WebElement addCartButton = driver.findElement(By.xpath("//*[@id=\"add_to_cart\"]/button"));
		  addCartButton.click();

		  Thread.sleep(10000);
		  WebElement proceedCheckButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/header/div[3]/div/div/div[4]/div[1]/div[2]/div[4]/a"));
		  proceedCheckButton.click();
		  
		  // Description validation
		  Thread.sleep(3000);
		  WebElement getDressDescription = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr/td[2]/p/a"));
		  System.out.println(getDressDescription.getText());
		  assertEquals("Faded Short Sleeve T-shirts", getDressDescription.getText());
		 
		  //In Stock Validation
		  WebElement inStockTag = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr/td[3]/span"));
		  System.out.println("Tag: " + inStockTag.getText());
		  assertEquals("In stock", inStockTag.getText());
		  
		  // Correctness of unit price Validation
		  WebElement getUnitPrice = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr/td[4]/span/span"));
		  String checkOutUnitPriceText = getUnitPrice.getText();
		  checkOutUnitPriceText = checkOutUnitPriceText.replace("$","");
		  double checkOutUnitPrice = Double.parseDouble(checkOutUnitPriceText);
		  System.out.println("Check out price: "+checkOutUnitPrice);
		  assertEquals(origPrice,checkOutUnitPrice);
		 
		  // Quantity Validation
		  WebElement getQuantity = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr/td[5]/input[2]")); 
		  double oldQuanity =  Double.parseDouble(getQuantity.getAttribute("value"));
		  System.out.println("Before incrementing quantity: "+oldQuanity);
		  oldQuanity+=1;
		 
		  
		  Thread.sleep(2000);
		  WebElement addQuantity = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr/td[5]/div/a[2]/span/i"));
		  addQuantity.click();
		  Thread.sleep(5000);
		  double newQuantity = Double.parseDouble(getQuantity.getAttribute("value"));
		  System.out.println("after incrementing quantity: "+newQuantity);
		  assertEquals(oldQuanity,newQuantity);
		  
		  // Total Price Validation
		  Thread.sleep(3000);
		  double totalUnitPriceCalculated = checkOutUnitPrice  * newQuantity;
		  System.out.println("Total Calculated price: " + totalUnitPriceCalculated);
		  
		  Thread.sleep(3000);
		  WebElement getTotalUnitsPrice = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr/td[6]/span")); 
		  String systemTotalPrice = getTotalUnitsPrice.getText();
		  systemTotalPrice = systemTotalPrice.replace("$", "");
		  double totalUnitPriceSystem = Double.parseDouble(systemTotalPrice);
		  System.out.println("Total System Price: "+ totalUnitPriceSystem);
		  
		  assertEquals(totalUnitPriceCalculated, totalUnitPriceSystem);
		  
		  // final total
		  WebElement getTotalShipping = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div[2]/table/tfoot/tr[3]/td[2]")); 
		  String getTotalShippingText = getTotalShipping.getText();
		  getTotalShippingText = getTotalShippingText.replace("$", "");
		  
		  WebElement getFinalTotal = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div[2]/table/tfoot/tr[4]/td[2]")); 
		  String getFinalTotalText = getFinalTotal.getText();
		  getFinalTotalText = getFinalTotalText.replace("$", "");
		  
		  WebElement getTaxPrice = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div[2]/table/tfoot/tr[5]/td[2]"));
		  String getTaxPriceText = getTaxPrice.getText();
		  getTaxPriceText = getTaxPriceText.replace("$", "");
		  
		  
		  double finalTotal = Double.parseDouble(getTaxPriceText) +totalUnitPriceSystem + Double.parseDouble(getTotalShippingText);
		  assertEquals(finalTotal, Double.parseDouble(getFinalTotalText));
		  
		  WebElement clickProceedButton = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/p[2]/a[1]"));
		  clickProceedButton.click();
		 
		  //Proceed after validation	
		  Thread.sleep(5000);
		  WebElement clickSigninButton = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/div[2]/form/div/p[2]/button"));
		  clickSigninButton.click();
		  
		  WebElement getSignInError = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div[1]"));
		  System.out.println("Sign in error: "+getSignInError.getText() );
		  assertNotNull(getSignInError);
		  
		 
		  // Creating an Account
		  WebElement emailText = driver.findElement(By.xpath("//*[@id=\"email_create\"]"));
		  JavascriptExecutor js = (JavascriptExecutor) driver;
		  js.executeScript("window.promptResponse=prompt('Please enter an email to register')");
		  //Alert alert = driver.switchTo().alert();
		  Thread.sleep(7000);
		  String emailInput = (String) js.executeScript("return window.promptResponse");
		  System.out.println("Email output is: " + emailInput);
		  
		  emailText.sendKeys(emailInput);
		 
		  WebElement createAccButton = driver.findElement(By.xpath("//*[@id=\"SubmitCreate\"]"));
		  createAccButton.click();
		  Thread.sleep(5000);
		  if(driver.findElements(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/div[1]/form/div/div[1]")).size() >0)  
		  {
			  WebElement getEmailError = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/div[1]/form/div/div[1]"));
			  System.out.println("Error Email: "+ getEmailError.getText());
			  while(driver.findElements(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/div[1]/form/div/div[1]")).size() > 0)
			  {
				  emailInput = "";
				  js.executeScript("window.promptResponse=prompt('Please enter another email to register')");
				  Thread.sleep(7000);
				  emailInput = (String) js.executeScript("return window.promptResponse");
				  System.out.println("Email output is: " + emailInput);
				  emailText.clear();
				  emailText.sendKeys(emailInput);
				  createAccButton.click();
				  Thread.sleep(5000);
			  } 
		  }
		  
		  Thread.sleep(2000);
		  // Registration		  													
		  WebElement genderSelect = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[1]/div[1]/div[1]/label/div/span/input"));
		  genderSelect.click();
		  
		  
		  WebElement firstNameText = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[1]/div[2]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your first name')");
		  Thread.sleep(5000);
		  String firstNameInput = (String) js.executeScript("return window.promptResponse");
		  firstNameText.sendKeys(firstNameInput);
		  
		  
		  Thread.sleep(1000);
		  WebElement lastNameText = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[1]/div[3]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your last name')");
		  Thread.sleep(5000);
		  String lastNameInput = (String) js.executeScript("return window.promptResponse");
		  lastNameText.sendKeys(lastNameInput);
		  
		  
		  Thread.sleep(1000); 
		  WebElement passwordText = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[1]/div[5]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your password')");
		  Thread.sleep(5000);
		  String passwordInput = (String) js.executeScript("return window.promptResponse");
		  passwordText.sendKeys(passwordInput);
		  
		  Thread.sleep(1000);
		  WebElement birthDateDay = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[1]/div[6]/div/div[1]/div/select/option[16]"));
		  birthDateDay.click();
		  
		  Thread.sleep(1000);
		  WebElement birthDateMonth = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[1]/div[6]/div/div[2]/div/select/option[12]"));
		  birthDateMonth.click();
		  
		  Thread.sleep(1000);
		  WebElement birthDateYear = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[1]/div[6]/div/div[3]/div/select/option[27]"));
		  birthDateYear.click();
		  
		  
		  Thread.sleep(1000);
		  WebElement companyText = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[3]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your company name')");
		  Thread.sleep(5000);
		  String companyInput = (String) js.executeScript("return window.promptResponse");
		  companyText.sendKeys(companyInput);
		  
		  
		  Thread.sleep(1000);
		  WebElement addressText = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[4]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your address')");
		  Thread.sleep(5000);
		  String addressInput = (String) js.executeScript("return window.promptResponse");
		  addressText.sendKeys(addressInput);
		  
		  
		  Thread.sleep(1000);
		  WebElement cityText = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[6]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your city')");
		  Thread.sleep(5000);
		  String cityInput = (String) js.executeScript("return window.promptResponse");
		  cityText.sendKeys(cityInput);
		  
		  
		  Thread.sleep(1000);
		  WebElement stateChoice = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[7]/div/select/option[13]"));
		  stateChoice.click();
		  
		  Thread.sleep(1000);
		  WebElement zipText = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[8]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your zip code')");
		  Thread.sleep(5000);
		  String zipInput = (String) js.executeScript("return window.promptResponse");
		  zipText.sendKeys(zipInput);
		  
		  
		  Thread.sleep(1000);
		  WebElement countryChoice = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[9]/div/select/option[2]"));
		  countryChoice.click();
		  
		  
		  Thread.sleep(1000);
		  WebElement additionalInfoText = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[10]/textarea"));
		  additionalInfoText.sendKeys("None");
		  
		  Thread.sleep(1000);
		  WebElement homePhone = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[12]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your home phone')");
		  Thread.sleep(5000);
		  String homePhoneInput = (String) js.executeScript("return window.promptResponse");
		  homePhone.sendKeys(homePhoneInput);
		  
		  Thread.sleep(1000);
		  WebElement mobilePhone = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[13]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your mobile phone')");
		  Thread.sleep(5000);
		  String mobilePhoneInput = (String) js.executeScript("return window.promptResponse");
		  mobilePhone.sendKeys(mobilePhoneInput);
		  
		  Thread.sleep(1000);
		  WebElement addressAllias = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[2]/p[14]/input"));
		  js.executeScript("window.promptResponse=prompt('Please enter your address allias')");
		  Thread.sleep(5000);
		  String addressAlliasInput = (String) js.executeScript("return window.promptResponse");
		  addressAllias.clear();
		  addressAllias.sendKeys(addressAlliasInput);
		  
		  
		  Thread.sleep(1000);
		  WebElement finalRegister = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div[4]/button"));
		  finalRegister.click();
		  while (driver.findElements(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div/ol/li")).size() >0) {
			  passwordText = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/div[1]/div[5]/input"));
			  Thread.sleep(1000);
			  js.executeScript("window.promptResponse=prompt('Please enter another password')");
			  Thread.sleep(5000);
			  passwordInput = (String) js.executeScript("return window.promptResponse");
			  passwordText.sendKeys(passwordInput);
			  Thread.sleep(1000);
			  finalRegister = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/div[4]/button"));
			  finalRegister.click();
			  Thread.sleep(4000);
		  }
		  	
		  Thread.sleep(2000);
		  // address validation
		  WebElement addressName = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/div/div[2]/div[1]/ul/li[2]"));
		  assertNotNull(addressName);
		 
		  WebElement addressCompany = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/div/div[2]/div[1]/ul/li[3]"));
		  assertNotNull(addressCompany);
		  
		  WebElement deliveryAddress1 = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/div/div[2]/div[1]/ul/li[4]"));
		  assertNotNull(deliveryAddress1);
		 
		  WebElement deliveryAddress2 = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/div/div[2]/div[1]/ul/li[5]"));
		  assertNotNull(deliveryAddress2);
		  
		  WebElement addressCountry = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/div/div[2]/div[1]/ul/li[6]"));
		  assertNotNull(addressCountry);
		  
		  WebElement addressPhoneMobile = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/div/div[2]/div[1]/ul/li[7]"));
		  assertNotNull(addressPhoneMobile);
		  
		  WebElement addressHomeMobile = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/div/div[2]/div[1]/ul/li[8]"));
		  assertNotNull(addressHomeMobile);
		  
		  // after validation, click checkout
		  WebElement checkOutFinalButton = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/p/button"));
		  checkOutFinalButton.click();
		  
		  // read terms validation
		  WebElement readTermsLink = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div/p[2]/a"));
		  readTermsLink.click();
		  
		  Thread.sleep(5000);
		  
		  WebElement closeTermsLink = driver.findElement(By.xpath("/html/body/div[2]/div/div/a"));
		  assertTrue(driver.findElements(By.xpath("/html/body/div[2]/div/div/a")).size()>0);
		  closeTermsLink.click();
		  
		  Thread.sleep(4000);
		  
		  // agree to terms
		  WebElement termAgreeButton = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/div/p[2]/div/span/input"));
		  termAgreeButton.click();
		  
		  
		  // final checkout
		  WebElement afterTermsCheckout = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/p/button"));
		  afterTermsCheckout.click();
		  
		  
		  
		  Thread.sleep(2000);
		  
		  
		  // pay using Cheque
		  WebElement payByCheckButton = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/div[3]/div[2]/div/p/a"));
		  payByCheckButton.click();
		  
		  
		  // confirm the order
		  WebElement confirmOrderButton = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/form/p/button"));
		  confirmOrderButton.click();
		  
		  
		  
		  // order complete message
		  WebElement orderCompleteMessage = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/p[1]"));
		  assertNotNull(orderCompleteMessage.getText());
		  
		  
		  // Beneficiary Cheque details
		  WebElement chequeAmount = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/span/strong"));
		  String chequeAmountText = chequeAmount.getText();
		  chequeAmountText = chequeAmountText.replace("$", "");
		  double chequeAmountValue = Double.parseDouble(chequeAmountText);
		  assertEquals(finalTotal, chequeAmountValue);
		  
		  WebElement chequeBeneficiary = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/strong[1]"));
		  assertNotNull(chequeBeneficiary.getText());
		  
		  WebElement chequeMail = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/strong[2]"));
		  assertNotNull(chequeMail.getText());
		  
		  WebElement orderReference = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/br[3]"));
		  assertNotNull(orderReference.getText());
		  
		  System.out.println("script finished sucessfully");
	}	

}
