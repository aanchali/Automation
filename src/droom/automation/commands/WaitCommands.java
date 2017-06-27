package droom.automation.commands;

/**
 * @author Abhishek Yadav
 * 
 */

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitCommands {

	/**
	 * @param myDriver
	 * @param wait
	 */

	public static void wait(WebDriver myDriver, long wait) {
		try {
			myDriver.manage().timeouts()
					.implicitlyWait(wait, TimeUnit.MILLISECONDS);
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
		}
	}

	/**
	 * @param myDriver
	 * @param locator
	 */

	public static void waitForElement(WebDriver myDriver,
			final String locatorType, final String locator) {
		try {
			(new WebDriverWait(myDriver, 60))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver driver) {

							final String idLocator = "id";
							final String nameLocator = "name";
							// final String xpathLocator = "xpath";
							final String cssSelectorLocator = "css selector";

							if (locatorType.equals(idLocator)) {
								return driver.findElement(By.id(locator));
							} else if (locatorType.equals(nameLocator)) {
								return driver.findElement(By.name(locator));
							} else if (locatorType.equals(cssSelectorLocator)) {
								return driver.findElement(By
										.cssSelector(locator));
							} else {
								return driver.findElement(By.xpath(locator));
							}
						}
					});
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
		}
	}

	/**
	 * @param myDriver
	 * @param locator
	 * @return
	 */

	public static boolean waitForElementBoolean(WebDriver myDriver,
			final String locator) {
		return (new WebDriverWait(myDriver, 60))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						if (driver.findElement(By.xpath(locator)).isDisplayed() == true) {
							return true;
						} else {
							return false;
						}
					}
				});
	}

	/**
	 * @param myDriver
	 * @param sheet1
	 * @param row
	 * @param screenshotPath
	 */

	public static boolean waitForElementDisplayedAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet1, int row) {
		final String yes = "Yes";
		final String idLocator = "id";
		final String nameLocator = "name";
		final String xpathLocator = "xpath";
		final String cssSelectorLocator = "css selector";
		final String execute = sheet1.getRow(row).getCell(3)
				.getStringCellValue();
		final String locatorType = sheet1.getRow(row).getCell(5)
				.getStringCellValue();
		final String locator = sheet1.getRow(row).getCell(6)
				.getStringCellValue();

		return (new WebDriverWait(myDriver, 60))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {

						WebElement myElement = null;
						if (locatorType.equals(idLocator)) {
							myElement = driver.findElement(By.id(locator));
						} else if (locatorType.equals(nameLocator)) {
							myElement = driver.findElement(By.name(locator));
						} else if (locatorType.equals(cssSelectorLocator)) {
							myElement = driver.findElement(By
									.cssSelector(locator));
						} else if (locatorType.equals(xpathLocator)) {
							myElement = driver.findElement(By.xpath(locator));
						} else if (locatorType.equals(xpathLocator)) {
							myElement = driver.findElement(By.xpath(locator));
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Not a valid locator type.");
						}
						if (execute.equals(yes)
								&& myElement.isDisplayed() == true) {
							return true;
						} else {
							return false;
						}
					}
				});
	}

	/**
	 * @param myDriver
	 * @param sheet1
	 * @param row
	 * @param screenshotPath
	 */

	public static boolean waitForElementEnabledAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet1, int row) {
		final String yes = "Yes";
		final String idLocator = "id";
		final String nameLocator = "name";
		final String xpathLocator = "xpath";
		final String cssSelectorLocator = "css selector";
		final String execute = sheet1.getRow(row).getCell(3)
				.getStringCellValue();
		final String locatorType = sheet1.getRow(row).getCell(5)
				.getStringCellValue();
		final String locator = sheet1.getRow(row).getCell(6)
				.getStringCellValue();

		return (new WebDriverWait(myDriver, 60))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {

						WebElement myElement = null;
						if (locatorType.equals(idLocator)) {
							myElement = driver.findElement(By.id(locator));
						} else if (locatorType.equals(nameLocator)) {
							myElement = driver.findElement(By.name(locator));
						} else if (locatorType.equals(cssSelectorLocator)) {
							myElement = driver.findElement(By
									.cssSelector(locator));
						} else if (locatorType.equals(xpathLocator)) {
							myElement = driver.findElement(By.xpath(locator));
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Not a valid locator type.");
						}
						if (execute.equals(yes)
								&& myElement.isDisplayed() == true
								&& myElement.isEnabled() == true) {
							return true;
						} else {
							return false;
						}
					}
				});
	}

	/**
	 * @param myDriver
	 * @param locator
	 */

	public static void waitForElementIsClickable(WebDriver myDriver,
			final String locatorType, final String locator) {

		try {

			final String idLocator = "id";
			final String nameLocator = "name";
			final String xpathLocator = "xpath";
			final String cssSelectorLocator = "css selector";
			WebDriverWait wait = new WebDriverWait(myDriver, 15);

			if (locatorType.equals(idLocator)) {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.id(locator)));
			} else if (locatorType.equals(nameLocator)) {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.name(locator)));
			} else if (locatorType.equals(cssSelectorLocator)) {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.cssSelector(locator)));
			} else if (locatorType.equals(xpathLocator)) {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.xpath(locator)));
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "Not a valid locator type.");
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
		}
	}

	/**
	 * @param myDriver
	 * @param start
	 */

	public static void waitForPageToLoad(WebDriver myDriver) {

		String loaded = "Page Loaded!!";
		String notLoaded = "Page did not Loaded!!";
		long start = System.currentTimeMillis();
		long wait = 60000 * 11;

		while ((System.currentTimeMillis() - start) < wait) {
			try {
				JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
				String loading = (String) myJSExecutor
						.executeScript("var s1 = 'Page Loaded!!'; var s2 = 'Page did not Loaded!!'; if(document.readyState === 'complete'){return s1;} else {return s2;}");
				if (loading.equals(loaded)) {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + loaded);
					break;
				} else if ((System.currentTimeMillis() - start) < (wait - 60000)) {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + notLoaded);
					break;
				}
			} catch (WebDriverException we) {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: "
						+ "WebDriver exception has been thrown : " + we);
			}
		}
	}

}
