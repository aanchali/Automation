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

public class CheckCommands {

	/**
	 * @param myDriver
	 * @param sheet1
	 * @param row
	 * @param screenshotPath
	 */

	public static void check(WebDriver myDriver, HSSFSheet sheet1, int row,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.click();
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
						if (myElement.isSelected() == true) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is checked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is not checked.");
							actual.setCellValue("Test case failed.");
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

	public static void checkAndWaitForNextElement(WebDriver myDriver,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.click();

						if (myElement.isSelected() == true) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is checked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is not checked.");
							actual.setCellValue("Test case failed.");
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
	 */

	public static void uncheck(WebDriver myDriver, HSSFSheet sheet1, int row,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.click();
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
						if (myElement.isSelected() == false) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is unchecked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is checked.");
							actual.setCellValue("Test case failed.");
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

	public static void uncheckAndWaitForNextElement(WebDriver myDriver,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.click();

						if (myElement.isSelected() == false) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is unchecked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is checked.");
							actual.setCellValue("Test case failed.");
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
	 */

	public static void checkAction(WebDriver myDriver, HSSFSheet sheet1,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).click(myElement)
								.perform();
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
						if (myElement.isSelected() == true) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is checked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is not checked.");
							actual.setCellValue("Test case failed.");
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

	public static void checkActionAndWaitForNextElement(WebDriver myDriver,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).click(myElement)
								.perform();

						if (myElement.isSelected() == true) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is checked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is not checked.");
							actual.setCellValue("Test case failed.");
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
	 */

	public static void uncheckAction(WebDriver myDriver, HSSFSheet sheet1,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).click(myElement)
								.perform();
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
						if (myElement.isSelected() == false) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is Unchecked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is checked.");
							actual.setCellValue("Test case failed.");
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

	public static void uncheckActionAndWaitForNextElement(WebDriver myDriver,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).click(myElement)
								.perform();

						if (myElement.isSelected() == false) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is unchecked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is checked.");
							actual.setCellValue("Test case failed.");
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
	 */

	public static void checkJS(WebDriver myDriver, HSSFSheet sheet1, int row,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
						myJSExecutor.executeScript("arguments[0].click();",
								myElement);
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
						if (myElement.isSelected() == true) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is checked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is not checked.");
							actual.setCellValue("Test case failed.");
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

	public static void checkJSAndWaitForNextElement(WebDriver myDriver,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
						myJSExecutor.executeScript("arguments[0].click();",
								myElement);

						if (myElement.isSelected() == true) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is checked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is not checked.");
							actual.setCellValue("Test case failed.");
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
	 */

	public static void uncheckJS(WebDriver myDriver, HSSFSheet sheet1, int row,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
						myJSExecutor.executeScript("arguments[0].click();",
								myElement);
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
						if (myElement.isSelected() == false) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is unchecked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is checked.");
							actual.setCellValue("Test case failed.");
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

	public static void uncheckJSAndWaitForNextElement(WebDriver myDriver,
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
		final Cell actual = sheet1.getRow(row).getCell(9);
		final Cell status = sheet1.getRow(row).getCell(10);
		status.setCellType(Cell.CELL_TYPE_BLANK);

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
						WaitCommands.waitForElementIsClickable(myDriver,
								locatorType, locator);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
						myJSExecutor.executeScript("arguments[0].click();",
								myElement);

						if (myElement.isSelected() == false) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out
									.println("["
											+ new Timestamp(new Date()
													.getTime())
											+ "] Custom Log: "
											+ "Checkbox is unchecked.");
							actual.setCellValue("Same as expected.");
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
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: "
									+ "Checkbox is checked.");
							actual.setCellValue("Test case failed.");
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

}
