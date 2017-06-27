package droom.automation.commands;

/**
 * @author Abhishek Yadav
 * 
 */

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;

public class GenericCommands {

	/**
	 * @param myDriver
	 * @param sheet1
	 * @param row
	 * @param screenshotPath
	 * @param msg1
	 * @param msg2
	 * @param locator
	 */

	public static void enterValueInHoneypotAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet1, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
		final String yes = "Yes";
		final String idLocator = "id";
		final String screenshotOnPass = sheet1.getRow(3).getCell(10)
				.getStringCellValue();
		final String testId = sheet1.getRow(row).getCell(2)
				.getStringCellValue();
		final String execute = sheet1.getRow(row).getCell(3)
				.getStringCellValue();
		final String locatorType = sheet1.getRow(row).getCell(5)
				.getStringCellValue();
		final String locator = sheet1.getRow(row).getCell(6)
				.getStringCellValue();
		final String input = sheet1.getRow(row).getCell(7).getStringCellValue();
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);
		final String value1;
		final String value2 = input;

		DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (yes.equals(execute)) {

				WebElement myElement = null;
				if (locatorType.equals(idLocator)) {
					myElement = myDriver.findElement(By.id(locator));
				} else {
					System.out.println("Locator type is not valid!!");
				}

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
						myJSExecutor.executeScript("document.getElementById('"
								+ locator + "').value='" + value2 + "';");
						value1 = myElement.getAttribute("value");
						if (null != sheet1.getRow(row + 1)
								|| "".equals(sheet1.getRow(row + 1))) {
							final String execute1 = sheet1.getRow(row + 1)
									.getCell(3).getStringCellValue();
							final String locatorType1 = sheet1.getRow(row + 1)
									.getCell(5).getStringCellValue();
							final String locator1 = sheet1.getRow(row + 1)
									.getCell(6).getStringCellValue();

							if (locator1 != "" && execute1.equals(yes)) {
								WaitCommands.waitForElement(myDriver,
										locatorType1, locator1);
							}
						}
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Button name is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if (yes.equals(screenshotOnPass)) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testId);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testId);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testId
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testId);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testId
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testId);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testId
						+ ": Test case not executed.");
				actual.setCellValue("Test case not executed.");
				status.setCellValue("NA");
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
			actual.setCellValue("WebDriver exception has been thrown. \n Error details: "
					+ we);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testId);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testId);
		}
	}

	/**
	 * @param myDriver
	 * @param sheet1
	 * @param row
	 * @param screenshotPath
	 * @param msg1
	 * @param msg2
	 * @param locator
	 */

	@SuppressWarnings("static-access")
	public static void enterCaptchaAndWaitForNextElement(WebDriver myDriver,
			HSSFSheet sheet1, int row, String screenshotPath,
			LogEntries browserLogEntries, LogEntries driverLogEntries,
			LogEntries performanceLogEntries) {
		final String yes = "Yes";
		final String idLocator = "id";
		final String nameLocator = "name";
		final String cssSelectorLocator = "css selector";
		final String xpathLocator = "xpath";
		final String screenshotOnPass = sheet1.getRow(3).getCell(10)
				.getStringCellValue();
		final String testId = sheet1.getRow(row).getCell(2)
				.getStringCellValue();
		final String execute = sheet1.getRow(row).getCell(3)
				.getStringCellValue();
		final String locatorType = sheet1.getRow(row).getCell(5)
				.getStringCellValue();
		final String locator = sheet1.getRow(row).getCell(6)
				.getStringCellValue();
		final String locator1 = "//img[@src='/Content/Images/tick.jpg']";
		final String input = sheet1.getRow(row).getCell(7).getStringCellValue();
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);
		final String value1;
		final String value2 = input;

		String msg1 = "Entering captcha...";
		String msg2 = "Captcha entered.";

		DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (yes.equals(execute)) {

				WebElement myElement = null;
				if (locatorType.equals(idLocator)) {
					myElement = myDriver.findElement(By.id(locator));
				} else if (locatorType.equals(nameLocator)) {
					myElement = myDriver.findElement(By.name(locator));
				} else if (locatorType.equals(cssSelectorLocator)) {
					myElement = myDriver.findElement(By.cssSelector(locator));
				} else if (locatorType.equals(xpathLocator)) {
					myElement = myDriver.findElement(By.xpath(locator));
				} else {
					System.out.println("Locator type is not valid!!");
				}

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						if (value2.equals("") || value2.equals(null)) {

							int gotoCase = 1;
							while (true) {
								switch (gotoCase) {
								case 1:
									System.out.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: " + msg1);
									myElement.sendKeys("!");
									myElement.sendKeys("1");
									myElement.sendKeys("q");
									myElement.sendKeys("@");
									myElement.sendKeys("2");
									myElement.sendKeys("w");
									myElement.sendKeys("#");
									myElement.sendKeys("3");
									myElement.sendKeys("e");
									myElement.sendKeys("$");
									myElement.sendKeys("4");
									myElement.sendKeys("r");
									myElement.sendKeys("%");
									myElement.sendKeys("5");
									myElement.sendKeys("t");
									try {
										Thread.sleep(5000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									if (myDriver.findElements(
											By.xpath(locator1)).size() > 0) {
										myElement.click();
										WaitCommands.wait(myDriver, 2000);
										System.out.println("["
												+ new Timestamp(new Date()
														.getTime())
												+ "] Custom Log: " + msg2);
									} else {
										System.out.println("["
												+ new Timestamp(new Date()
														.getTime())
												+ "] Custom Log: " + "Re-"
												+ msg1);
										myElement.clear();
										gotoCase = 1;
										continue;
									}
								}
								System.out.println("["
										+ new Timestamp(new Date().getTime())
										+ "] Custom Log: " + testId
										+ ": Test case passed.");
								actual.setCellValue("Same as expected. \n"
										+ msg2);
								status.setCellValue("PASS");
								if (yes.equals(screenshotOnPass)) {
									TakeScreenshotCommands.screenshotOnPass(
											myDriver, screenshotPath, testId);
								}
							}
						} else if (!value2.equals("") || !value2.equals(null)) {
							myElement.sendKeys(value2);
							value1 = myElement.getAttribute("value");
							if (value1.equals(value2)) {
								System.out.println("["
										+ new Timestamp(new Date().getTime())
										+ "] Custom Log: " + testId
										+ ": Test case passed.");
								System.out.println("["
										+ new Timestamp(new Date().getTime())
										+ "] Custom Log: "
										+ "Entered value is: " + value1);
								actual.setCellValue("Same as expected. \n"
										+ value1);
								status.setCellValue("PASS");
								if (yes.equals(screenshotOnPass)) {
									TakeScreenshotCommands.screenshotOnPass(
											myDriver, screenshotPath, testId);
								}
							} else {
								System.out.println("["
										+ new Timestamp(new Date().getTime())
										+ "] Custom Log: " + testId
										+ ": Test case failed.");
								actual.setCellValue("Test case failed. \n Actual Result: "
										+ value1);
								status.setCellValue("FAIL");
								TakeScreenshotCommands.screenshotOnFail(
										myDriver, screenshotPath, testId);
							}

							Cell cell = sheet1
									.getRow(row + 1)
									.getCell(
											2,
											sheet1.getRow(row + 1).CREATE_NULL_AS_BLANK);
							if (!cell.getStringCellValue().equals("")) {
								final String execute1 = sheet1.getRow(row + 1)
										.getCell(3).getStringCellValue();
								final String locatorType2 = sheet1
										.getRow(row + 1).getCell(5)
										.getStringCellValue();
								final String locator2 = sheet1.getRow(row + 1)
										.getCell(6).getStringCellValue();

								if (locator1 != "" && execute1.equals(yes)) {
									WaitCommands.waitForElement(myDriver,
											locatorType2, locator2);
								}
							}
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testId
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testId);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testId
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testId);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testId
						+ ": Test case not executed.");
				actual.setCellValue("Test case not executed.");
				status.setCellValue("NA");
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
			actual.setCellValue("WebDriver exception has been thrown. \n Error details: "
					+ we);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testId);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testId);
		}
	}
}
