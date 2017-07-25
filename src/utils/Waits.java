package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {

	WebDriver driver;

	public void waitForPresenceOfElement(WebDriver driver, String identifier, String value) {
		WebDriverWait wait = new WebDriverWait(driver, 2000);
		if (identifier == "xpath") {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(value)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(value)));
		} else if (identifier == "cssSelector") {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(value)));
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(value)));
		} else if (identifier =="id"){
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value)));
			wait.until(ExpectedConditions.elementToBeClickable(By.id(value)));

		}else if (identifier =="name"){
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(value)));
			wait.until(ExpectedConditions.elementToBeClickable(By.name(value)));
		}
	}

	public void waitForVisibilityOfElement(WebDriver driver,String identifier, String value) 
{
		WebDriverWait wait = new WebDriverWait(driver,9000);
		if (identifier == "xpath")
	   {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(value)));
   	   } 
		else if(identifier=="cssSelector") 
   	   {
   	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(value)));
   	   } else 
   	   {
   	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(value)));
   	   }
 }
	
	public void waitForElementToBeClickable(WebDriver driver, String identifier, String value) {
		WebDriverWait wait = new WebDriverWait(driver, 3000);
		if (identifier == "xpath") {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(value)));
		} else if (identifier == "cssSelector") {
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(value)));
		} else {
			wait.until(ExpectedConditions.elementToBeClickable(By.id(value)));
		}
	}
}



