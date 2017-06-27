package droom.automation.commons;

/**
 * @author Abhishek Yadav
 * 
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MiscCommons {

	/**
	 * @param myDriver
	 * @param locatorType
	 * @param locator
	 * @return
	 */

	public boolean nextScenario(WebDriver myDriver, String locatorType,
			String locator) {
		try {
			final String idLocator = "id";
			final String nameLocator = "name";
			final String cssSelectorLocator = "css selector";
			final String xpathLocator = "xpath";

			WebElement myElement = null;

			if (locatorType.equals(idLocator)) {
				myElement = myDriver.findElement(By.id(locator));
			} else if (locatorType.equals(nameLocator)) {
				myElement = myDriver.findElement(By.name(locator));
			} else if (locatorType.equals(cssSelectorLocator)) {
				myElement = myDriver.findElement(By.cssSelector(locator));
			} else if (locatorType.equals(xpathLocator)) {
				myElement = myDriver.findElement(By.xpath(locator));
			}

			if (myElement.isDisplayed() == true) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception has been thrown : " + e);
			return false;
		}
	}

}
