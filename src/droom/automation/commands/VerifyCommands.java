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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;

public class VerifyCommands {

	/**
	 * @param myDriver
	 * @param sheet1
	 * @param row
	 * @param screenshotPath
	 */
	public static void verifyInputValue(WebDriver myDriver, HSSFSheet sheet1,
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
						value1 = myElement.getAttribute("value");

						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Value is: " + value1);
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

	public static void verifyInputValueAndWaitForNextElement(
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
									+ "] Custom Log: " + "Value is: " + value1);
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
	 */
	public static void verifyDisabledElementText(WebDriver myDriver,
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
					// JSCommands.jsScrollBy(myDriver, 0, 5);
					Actions myAction = new Actions(myDriver);
					myAction.moveToElement(myElement).perform();
					value1 = myElement.getAttribute("value");

					if (value1.equals(value2)) {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testId
								+ ": Test case passed.");
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + "Value is: " + value1);
						actual.setCellValue("Same as expected. \n" + value1);
						status.setCellValue("PASS");
						if (yes.equals(screenshotOnPass)) {
							TakeScreenshotCommands.screenshotOnPass(myDriver,
									screenshotPath, testId);
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

	public static void verifyDisabledElementTextAndWaitForNextElement(
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
					// JSCommands.jsScrollBy(myDriver, 0, 5);
					Actions myAction = new Actions(myDriver);
					myAction.moveToElement(myElement).perform();
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
							WaitCommands.waitForElement(myDriver, locatorType1,
									locator1);
						}
					}
					if (value1.equals(value2)) {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testId
								+ ": Test case passed.");
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + "Value is: " + value1);
						actual.setCellValue("Same as expected. \n" + value1);
						status.setCellValue("PASS");
						if (yes.equals(screenshotOnPass)) {
							TakeScreenshotCommands.screenshotOnPass(myDriver,
									screenshotPath, testId);
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
	public static void verifyDisabledInputValue(WebDriver myDriver,
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
					// JSCommands.jsScrollBy(myDriver, 0, 5);
					Actions myAction = new Actions(myDriver);
					myAction.moveToElement(myElement).perform();
					value1 = myElement.getAttribute("value");

					if (value1.equals(value2)) {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testId
								+ ": Test case passed.");
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + "Value is: " + value1);
						actual.setCellValue("Same as expected. \n" + value1);
						status.setCellValue("PASS");
						if (yes.equals(screenshotOnPass)) {
							TakeScreenshotCommands.screenshotOnPass(myDriver,
									screenshotPath, testId);
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

	public static void verifyDisabledInputValueAndWaitForNextElement(
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
					// JSCommands.jsScrollBy(myDriver, 0, 5);
					Actions myAction = new Actions(myDriver);
					myAction.moveToElement(myElement).perform();
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
							WaitCommands.waitForElement(myDriver, locatorType1,
									locator1);
						}
					}
					if (value1.equals(value2)) {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testId
								+ ": Test case passed.");
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + "Value is: " + value1);
						actual.setCellValue("Same as expected. \n" + value1);
						status.setCellValue("PASS");
						if (yes.equals(screenshotOnPass)) {
							TakeScreenshotCommands.screenshotOnPass(myDriver,
									screenshotPath, testId);
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
	public static void verifyElementPresent(WebDriver myDriver,
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
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();

						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testId
								+ ": Test case passed.");
						actual.setCellValue("Same as expected. \n");
						status.setCellValue("PASS");
						if (yes.equals(screenshotOnPass)) {
							TakeScreenshotCommands.screenshotOnPass(myDriver,
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

	public static void verifyElementPresentAndWaitForNextElement(
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
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
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
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testId
								+ ": Test case passed.");
						actual.setCellValue("Same as expected. \n");
						status.setCellValue("PASS");
						if (yes.equals(screenshotOnPass)) {
							TakeScreenshotCommands.screenshotOnPass(myDriver,
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
	public static void verifyElementNotPresent(WebDriver myDriver,
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
					// JSCommands.jsScrollBy(myDriver, 0, 5);

					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case failed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testId);
				} else {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
					actual.setCellValue("Same as expected.");
					status.setCellValue("PASS");
					if (yes.equals(screenshotOnPass)) {
						TakeScreenshotCommands.screenshotOnPass(myDriver,
								screenshotPath, testId);
					}
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testId
						+ ": Test case not executed.");
				actual.setCellValue("Test case not executed.");
				status.setCellValue("NA");
			}
		} catch (NoSuchElementException ne) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: " + testId + ": Test case passed.");
			actual.setCellValue("Same as expected.");
			status.setCellValue("PASS");
			if (yes.equals(screenshotOnPass)) {
				TakeScreenshotCommands.screenshotOnPass(myDriver,
						screenshotPath, testId);
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

	public static void verifyElementNotPresentAndWaitForNextElement(
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

				if (myElement.isDisplayed() == false) {
					// JSCommands.jsScrollBy(myDriver, 0, 5);
					if (null != sheet1.getRow(row + 1)
							|| "".equals(sheet1.getRow(row + 1))) {
						final String execute1 = sheet1.getRow(row + 1)
								.getCell(3).getStringCellValue();
						final String locatorType1 = sheet1.getRow(row + 1)
								.getCell(5).getStringCellValue();
						final String locator1 = sheet1.getRow(row + 1)
								.getCell(6).getStringCellValue();

						if (locator1 != "" && execute1.equals(yes)) {
							WaitCommands.waitForElement(myDriver, locatorType1,
									locator1);
						}
					}

					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case failed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testId);
				} else {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
					actual.setCellValue("Same as expected.");
					status.setCellValue("PASS");
					if (yes.equals(screenshotOnPass)) {
						TakeScreenshotCommands.screenshotOnPass(myDriver,
								screenshotPath, testId);
					}
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testId
						+ ": Test case not executed.");
				actual.setCellValue("Test case not executed.");
				status.setCellValue("NA");
			}
		} catch (NoSuchElementException ne) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: " + testId + ": Test case passed.");
			actual.setCellValue("Same as expected.");
			status.setCellValue("PASS");
			if (yes.equals(screenshotOnPass)) {
				TakeScreenshotCommands.screenshotOnPass(myDriver,
						screenshotPath, testId);
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
	public static void verifyTextPresent(WebDriver myDriver, HSSFSheet sheet1,
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
						value1 = myElement.getText();

						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Value is: " + value1);
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

	public static void verifyTextPresentAndWaitForNextElement(
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
						value1 = myElement.getText();
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
									+ "] Custom Log: " + "Value is: " + value1);
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
	 */
	public static void verifyTipsyToolTipText(WebDriver myDriver,
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
						myAction.moveToElement(myElement).perform();
						value1 = myElement.getAttribute("original-title");

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

	public static void verifyTipsyToolTipTextAndWaitForNextElement(
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
						value1 = myElement.getAttribute("original-title");
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
	 */

	public static void verifyPartialURL(WebDriver myDriver, HSSFSheet sheet1,
			int row, String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
		final String yes = "Yes";
		final String screenshotOnPass = sheet1.getRow(3).getCell(10)
				.getStringCellValue();
		final String testId = sheet1.getRow(row).getCell(2)
				.getStringCellValue();
		final String execute = sheet1.getRow(row).getCell(3)
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

				// JSCommands.jsScrollBy(myDriver, 0, 5);
				value1 = myDriver.getCurrentUrl();

				if (value1.contains(value2)) {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + "URL is: " + value1);
					actual.setCellValue("Same as expected. \n" + value1);
					status.setCellValue("PASS");
					if (yes.equals(screenshotOnPass)) {
						TakeScreenshotCommands.screenshotOnPass(myDriver,
								screenshotPath, testId);
					}
				} else {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case failed.");
					actual.setCellValue("Test case failed. \n Actual Result: "
							+ value1);
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

	public static void verifyPartialURLAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet1, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
		final String yes = "Yes";
		final String screenshotOnPass = sheet1.getRow(3).getCell(10)
				.getStringCellValue();
		final String testId = sheet1.getRow(row).getCell(2)
				.getStringCellValue();
		final String execute = sheet1.getRow(row).getCell(3)
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
	
				// JSCommands.jsScrollBy(myDriver, 0, 5);
				value1 = myDriver.getCurrentUrl();
				if (null != sheet1.getRow(row + 1)
						|| "".equals(sheet1.getRow(row + 1))) {
					final String execute1 = sheet1.getRow(row + 1).getCell(3)
							.getStringCellValue();
					final String locatorType1 = sheet1.getRow(row + 1)
							.getCell(5).getStringCellValue();
					final String locator1 = sheet1.getRow(row + 1).getCell(6)
							.getStringCellValue();
	
					if (locator1 != "" && execute1.equals(yes)) {
						WaitCommands.waitForElement(myDriver, locatorType1,
								locator1);
					}
				}
				if (value1.contains(value2)) {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + "URL is: " + value1);
					actual.setCellValue("Same as expected. \n" + value1);
					status.setCellValue("PASS");
					if (yes.equals(screenshotOnPass)) {
						TakeScreenshotCommands.screenshotOnPass(myDriver,
								screenshotPath, testId);
					}
				} else {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case failed.");
					actual.setCellValue("Test case failed. \n Actual Result: "
							+ value1);
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

	public static void verifyCurrentURLAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet1, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
		final String yes = "Yes";
		final String screenshotOnPass = sheet1.getRow(3).getCell(10)
				.getStringCellValue();
		final String testId = sheet1.getRow(row).getCell(2)
				.getStringCellValue();
		final String execute = sheet1.getRow(row).getCell(3)
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

				// JSCommands.jsScrollBy(myDriver, 0, 5);
				value1 = myDriver.getCurrentUrl();
				if (null != sheet1.getRow(row + 1)
						|| "".equals(sheet1.getRow(row + 1))) {
					final String execute1 = sheet1.getRow(row + 1).getCell(3)
							.getStringCellValue();
					final String locatorType1 = sheet1.getRow(row + 1)
							.getCell(5).getStringCellValue();
					final String locator1 = sheet1.getRow(row + 1).getCell(6)
							.getStringCellValue();

					if (locator1 != "" && execute1.equals(yes)) {
						WaitCommands.waitForElement(myDriver, locatorType1,
								locator1);
					}
				}
				if (value1.equals(value2)) {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + "URL is: " + value1);
					actual.setCellValue("Same as expected. \n" + value1);
					status.setCellValue("PASS");
					if (yes.equals(screenshotOnPass)) {
						TakeScreenshotCommands.screenshotOnPass(myDriver,
								screenshotPath, testId);
					}
				} else {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case failed.");
					actual.setCellValue("Test case failed. \n Actual Result: "
							+ value1);
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

	public static void verifyCurrentURL(WebDriver myDriver, HSSFSheet sheet1,
			int row, String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
		final String yes = "Yes";
		final String screenshotOnPass = sheet1.getRow(3).getCell(10)
				.getStringCellValue();
		final String testId = sheet1.getRow(row).getCell(2)
				.getStringCellValue();
		final String execute = sheet1.getRow(row).getCell(3)
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

				// JSCommands.jsScrollBy(myDriver, 0, 5);
				value1 = myDriver.getCurrentUrl();

				if (value1.equals(value2)) {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + "URL is: " + value1);
					actual.setCellValue("Same as expected. \n" + value1);
					status.setCellValue("PASS");
					if (yes.equals(screenshotOnPass)) {
						TakeScreenshotCommands.screenshotOnPass(myDriver,
								screenshotPath, testId);
					}
				} else {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case failed.");
					actual.setCellValue("Test case failed. \n Actual Result: "
							+ value1);
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
	public static void verifyPartialTextPresent(WebDriver myDriver, HSSFSheet sheet1,
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
						value1 = myElement.getText();
	
						if (value1.contains(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Value is: " + value1);
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

	public static void verifyPartialTextPresentAndWaitForNextElement(
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
						value1 = myElement.getText();
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
						if (value1.contains(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Value is: " + value1);
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

}
