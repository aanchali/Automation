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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;

import droom.automation.commons.SelectCalendarDate;

public class CalendarCommands {

	/**
	 * @param myDriver
	 * @param sheet1
	 * @param row
	 * @param screenshotPath
	 */

	public static void selectDateEnglish(WebDriver myDriver, HSSFSheet sheet1,
			int row, String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
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
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.click();
						SelectCalendarDate.addDateEnglish(myDriver, value2);
						value1 = myElement.getAttribute("value");
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Button name is: "
									+ value1);
							System.out.println("Browser Log: "
									+ browserLogEntries.getAll());
							System.out.println("WebDriver Log: "
									+ driverLogEntries.getAll());
							System.out.println("Performance Log: "
									+ performanceLogEntries.getAll());
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
							System.out.println("Browser Log: "
									+ browserLogEntries.getAll());
							System.out.println("WebDriver Log: "
									+ driverLogEntries.getAll());
							System.out.println("Performance Log: "
									+ performanceLogEntries.getAll());
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
						System.out.println("Browser Log: "
								+ browserLogEntries.getAll());
						System.out.println("WebDriver Log: "
								+ driverLogEntries.getAll());
						System.out.println("Performance Log: "
								+ performanceLogEntries.getAll());
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
					System.out.println("Browser Log: "
							+ browserLogEntries.getAll());
					System.out.println("WebDriver Log: "
							+ driverLogEntries.getAll());
					System.out.println("Performance Log: "
							+ performanceLogEntries.getAll());
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testId);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testId
						+ ": Test case not executed.");
				System.out
						.println("Browser Log: " + browserLogEntries.getAll());
				System.out.println("WebDriver Log: "
						+ driverLogEntries.getAll());
				System.out.println("Performance Log: "
						+ performanceLogEntries.getAll());
				actual.setCellValue("Test case not executed.");
				status.setCellValue("NA");
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
			System.out.println("Browser Log: " + browserLogEntries.getAll());
			System.out.println("WebDriver Log: " + driverLogEntries.getAll());
			System.out.println("Performance Log: "
					+ performanceLogEntries.getAll());
			actual.setCellValue("WebDriver exception has been thrown. \n Error details: "
					+ we);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testId);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			System.out.println("Browser Log: " + browserLogEntries.getAll());
			System.out.println("WebDriver Log: " + driverLogEntries.getAll());
			System.out.println("Performance Log: "
					+ performanceLogEntries.getAll());
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
	 */

	public static void selectDateEnglishAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet1, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
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
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.click();
						SelectCalendarDate.addDateEnglish(myDriver, value2);
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
							System.out.println("Browser Log: "
									+ browserLogEntries.getAll());
							System.out.println("WebDriver Log: "
									+ driverLogEntries.getAll());
							System.out.println("Performance Log: "
									+ performanceLogEntries.getAll());
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
							System.out.println("Browser Log: "
									+ browserLogEntries.getAll());
							System.out.println("WebDriver Log: "
									+ driverLogEntries.getAll());
							System.out.println("Performance Log: "
									+ performanceLogEntries.getAll());
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
						System.out.println("Browser Log: "
								+ browserLogEntries.getAll());
						System.out.println("WebDriver Log: "
								+ driverLogEntries.getAll());
						System.out.println("Performance Log: "
								+ performanceLogEntries.getAll());
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
					System.out.println("Browser Log: "
							+ browserLogEntries.getAll());
					System.out.println("WebDriver Log: "
							+ driverLogEntries.getAll());
					System.out.println("Performance Log: "
							+ performanceLogEntries.getAll());
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testId);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testId
						+ ": Test case not executed.");
				System.out
						.println("Browser Log: " + browserLogEntries.getAll());
				System.out.println("WebDriver Log: "
						+ driverLogEntries.getAll());
				System.out.println("Performance Log: "
						+ performanceLogEntries.getAll());
				actual.setCellValue("Test case not executed.");
				status.setCellValue("NA");
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
			System.out.println("Browser Log: " + browserLogEntries.getAll());
			System.out.println("WebDriver Log: " + driverLogEntries.getAll());
			System.out.println("Performance Log: "
					+ performanceLogEntries.getAll());
			actual.setCellValue("WebDriver exception has been thrown. \n Error details: "
					+ we);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testId);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			System.out.println("Browser Log: " + browserLogEntries.getAll());
			System.out.println("WebDriver Log: " + driverLogEntries.getAll());
			System.out.println("Performance Log: "
					+ performanceLogEntries.getAll());
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
	 */

	public static void selectDateEnglishAction(WebDriver myDriver,
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
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).click(myElement)
								.perform();
						SelectCalendarDate.addDateEnglish(myDriver, value2);
						value1 = myElement.getAttribute("value");
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Button name is: "
									+ value1);
							System.out.println("Browser Log: "
									+ browserLogEntries.getAll());
							System.out.println("WebDriver Log: "
									+ driverLogEntries.getAll());
							System.out.println("Performance Log: "
									+ performanceLogEntries.getAll());
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
							System.out.println("Browser Log: "
									+ browserLogEntries.getAll());
							System.out.println("WebDriver Log: "
									+ driverLogEntries.getAll());
							System.out.println("Performance Log: "
									+ performanceLogEntries.getAll());
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
						System.out.println("Browser Log: "
								+ browserLogEntries.getAll());
						System.out.println("WebDriver Log: "
								+ driverLogEntries.getAll());
						System.out.println("Performance Log: "
								+ performanceLogEntries.getAll());
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
					System.out.println("Browser Log: "
							+ browserLogEntries.getAll());
					System.out.println("WebDriver Log: "
							+ driverLogEntries.getAll());
					System.out.println("Performance Log: "
							+ performanceLogEntries.getAll());
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testId);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testId
						+ ": Test case not executed.");
				System.out
						.println("Browser Log: " + browserLogEntries.getAll());
				System.out.println("WebDriver Log: "
						+ driverLogEntries.getAll());
				System.out.println("Performance Log: "
						+ performanceLogEntries.getAll());
				actual.setCellValue("Test case not executed.");
				status.setCellValue("NA");
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
			System.out.println("Browser Log: " + browserLogEntries.getAll());
			System.out.println("WebDriver Log: " + driverLogEntries.getAll());
			System.out.println("Performance Log: "
					+ performanceLogEntries.getAll());
			actual.setCellValue("WebDriver exception has been thrown. \n Error details: "
					+ we);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testId);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			System.out.println("Browser Log: " + browserLogEntries.getAll());
			System.out.println("WebDriver Log: " + driverLogEntries.getAll());
			System.out.println("Performance Log: "
					+ performanceLogEntries.getAll());
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
	 */

	public static void selectDateEnglishActionAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet1, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
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
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).click(myElement)
								.perform();
						SelectCalendarDate.addDateEnglish(myDriver, value2);
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
							System.out.println("Browser Log: "
									+ browserLogEntries.getAll());
							System.out.println("WebDriver Log: "
									+ driverLogEntries.getAll());
							System.out.println("Performance Log: "
									+ performanceLogEntries.getAll());
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
							System.out.println("Browser Log: "
									+ browserLogEntries.getAll());
							System.out.println("WebDriver Log: "
									+ driverLogEntries.getAll());
							System.out.println("Performance Log: "
									+ performanceLogEntries.getAll());
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
						System.out.println("Browser Log: "
								+ browserLogEntries.getAll());
						System.out.println("WebDriver Log: "
								+ driverLogEntries.getAll());
						System.out.println("Performance Log: "
								+ performanceLogEntries.getAll());
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
					System.out.println("Browser Log: "
							+ browserLogEntries.getAll());
					System.out.println("WebDriver Log: "
							+ driverLogEntries.getAll());
					System.out.println("Performance Log: "
							+ performanceLogEntries.getAll());
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testId);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testId
						+ ": Test case not executed.");
				System.out
						.println("Browser Log: " + browserLogEntries.getAll());
				System.out.println("WebDriver Log: "
						+ driverLogEntries.getAll());
				System.out.println("Performance Log: "
						+ performanceLogEntries.getAll());
				actual.setCellValue("Test case not executed.");
				status.setCellValue("NA");
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
			System.out.println("Browser Log: " + browserLogEntries.getAll());
			System.out.println("WebDriver Log: " + driverLogEntries.getAll());
			System.out.println("Performance Log: "
					+ performanceLogEntries.getAll());
			actual.setCellValue("WebDriver exception has been thrown. \n Error details: "
					+ we);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testId);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			System.out.println("Browser Log: " + browserLogEntries.getAll());
			System.out.println("WebDriver Log: " + driverLogEntries.getAll());
			System.out.println("Performance Log: "
					+ performanceLogEntries.getAll());
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testId);
		}
	}

}
