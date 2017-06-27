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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;

import droom.automation.excel.CellActions;
import droom.automation.globals.CommandExecutionStatus;
import droom.automation.globals.CommandGlobalVariable;

public class TypeCommands {

	static DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");

	/**
	 * @param myDriver
	 * @param sheet
	 * @param row
	 * @param screenshotPath
	 */
	public static void type(WebDriver myDriver, HSSFSheet sheet, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.clear();
						// myElement.click();
						myElement.sendKeys(value2);
						value1 = myElement.getAttribute("value");

						CommandExecutionStatus.testCaseStatusForTextbox(
								myDriver, value1, value2, testData, actual,
								status, screenshotPath);

					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	public static void typeAndWaitForNextElement(WebDriver myDriver,
			HSSFSheet sheet, int row, String screenshotPath,
			LogEntries browserLogEntries, LogEntries driverLogEntries,
			LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.clear();
						// myElement.click();
						myElement.sendKeys(value2);
						value1 = myElement.getAttribute("value");
						if (null != sheet.getRow(row + 1)
								|| "".equals(sheet.getRow(row + 1))) {
							final String execute1 = sheet.getRow(row + 1)
									.getCell(3).getStringCellValue();
							final String locatorType1 = sheet.getRow(row + 1)
									.getCell(5).getStringCellValue();
							final String locator1 = sheet.getRow(row + 1)
									.getCell(6).getStringCellValue();

							if (locator1 != "" && execute1.equals(testData[0])) {
								WaitCommands.waitForElement(myDriver,
										locatorType1, locator1);
							}
						}
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	/**
	 * @param myDriver
	 * @param sheet
	 * @param row
	 * @param screenshotPath
	 */
	public static void typeAndReturn(WebDriver myDriver, HSSFSheet sheet,
			int row, String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.clear();
						// myElement.click();
						myElement.sendKeys(value2);
						myElement.sendKeys(Keys.ENTER);
						value1 = myElement.getAttribute("value");

						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	public static void typeAndReturnAndWaitForNextElement(WebDriver myDriver,
			HSSFSheet sheet, int row, String screenshotPath,
			LogEntries browserLogEntries, LogEntries driverLogEntries,
			LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.clear();
						// myElement.click();
						myElement.sendKeys(value2);
						myElement.sendKeys(Keys.ENTER);
						value1 = myElement.getAttribute("value");
						if (null != sheet.getRow(row + 1)
								|| "".equals(sheet.getRow(row + 1))) {
							final String execute1 = sheet.getRow(row + 1)
									.getCell(3).getStringCellValue();
							final String locatorType1 = sheet.getRow(row + 1)
									.getCell(5).getStringCellValue();
							final String locator1 = sheet.getRow(row + 1)
									.getCell(6).getStringCellValue();

							if (locator1 != "" && execute1.equals(testData[0])) {
								WaitCommands.waitForElement(myDriver,
										locatorType1, locator1);
							}
						}
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	/**
	 * @param myDriver
	 * @param sheet
	 * @param row
	 * @param screenshotPath
	 * @param str
	 */
	public static void typeFromInputDialog(WebDriver myDriver, HSSFSheet sheet,
			int row, String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries,
			String str) {
		// String str = JOptionPane.showInputDialog(null,
		// "Enter captcha manually: ", "BancPay", 1);

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.clear();
						// myElement.click();
						myElement.sendKeys(value2);
						myElement.sendKeys(Keys.ENTER);
						value1 = myElement.getAttribute("value");

						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	public static void typeFromInputDialogAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries,
			String str) {
		// String str = JOptionPane.showInputDialog(null,
		// "Enter captcha manually: ", "BancPay", 1);

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.clear();
						// myElement.click();
						myElement.sendKeys(value2);
						myElement.sendKeys(Keys.ENTER);
						value1 = myElement.getAttribute("value");
						if (null != sheet.getRow(row + 1)
								|| "".equals(sheet.getRow(row + 1))) {
							final String execute1 = sheet.getRow(row + 1)
									.getCell(3).getStringCellValue();
							final String locatorType1 = sheet.getRow(row + 1)
									.getCell(5).getStringCellValue();
							final String locator1 = sheet.getRow(row + 1)
									.getCell(6).getStringCellValue();

							if (locator1 != "" && execute1.equals(testData[0])) {
								WaitCommands.waitForElement(myDriver,
										locatorType1, locator1);
							}
						}
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	/**
	 * @param myDriver
	 * @param sheet
	 * @param row
	 * @param screenshotPath
	 */
	public static void typeAction(WebDriver myDriver, HSSFSheet sheet, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						myElement.clear();
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement)
						// .click()
								.sendKeys(value2).perform();
						value1 = myElement.getAttribute("value");

						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	public static void typeActionAndWaitForNextElement(WebDriver myDriver,
			HSSFSheet sheet, int row, String screenshotPath,
			LogEntries browserLogEntries, LogEntries driverLogEntries,
			LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						myElement.clear();
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement)
						// .click()
								.sendKeys(value2).perform();
						value1 = myElement.getAttribute("value");
						if (null != sheet.getRow(row + 1)
								|| "".equals(sheet.getRow(row + 1))) {
							final String execute1 = sheet.getRow(row + 1)
									.getCell(3).getStringCellValue();
							final String locatorType1 = sheet.getRow(row + 1)
									.getCell(5).getStringCellValue();
							final String locator1 = sheet.getRow(row + 1)
									.getCell(6).getStringCellValue();

							if (locator1 != "" && execute1.equals(testData[0])) {
								WaitCommands.waitForElement(myDriver,
										locatorType1, locator1);
							}
						}
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	/**
	 * @param myDriver
	 * @param sheet
	 * @param row
	 * @param screenshotPath
	 */

	public static void typeAndReturnAction(WebDriver myDriver, HSSFSheet sheet,
			int row, String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						myElement.clear();
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement)
								// .click()
								.sendKeys(value2).sendKeys(Keys.ENTER)
								.perform();
						value1 = myElement.getAttribute("value");

						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	public static void typeAndReturnActionAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						myElement.clear();
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement)
								// .click()
								.sendKeys(value2).sendKeys(Keys.ENTER)
								.perform();
						value1 = myElement.getAttribute("value");
						if (null != sheet.getRow(row + 1)
								|| "".equals(sheet.getRow(row + 1))) {
							final String execute1 = sheet.getRow(row + 1)
									.getCell(3).getStringCellValue();
							final String locatorType1 = sheet.getRow(row + 1)
									.getCell(5).getStringCellValue();
							final String locator1 = sheet.getRow(row + 1)
									.getCell(6).getStringCellValue();

							if (locator1 != "" && execute1.equals(testData[0])) {
								WaitCommands.waitForElement(myDriver,
										locatorType1, locator1);
							}
						}
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	/**
	 * @param myDriver
	 * @param sheet
	 * @param row
	 * @param screenshotPath
	 * @param str
	 */

	public static void typeFromInputDialogAction(WebDriver myDriver,
			HSSFSheet sheet, int row, String screenshotPath,
			LogEntries browserLogEntries, LogEntries driverLogEntries,
			LogEntries performanceLogEntries, String str) {
		// String str = JOptionPane.showInputDialog(null,
		// "Enter captcha manually: ", "BancPay", 1);

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						myElement.clear();
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement)
								// .click()
								.sendKeys(value2).sendKeys(Keys.ENTER)
								.perform();
						value1 = myElement.getAttribute("value");

						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	public static void typeFromInputDialogActionAndWaitForNextElement(
			WebDriver myDriver, HSSFSheet sheet, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries,
			String str) {
		// String str = JOptionPane.showInputDialog(null,
		// "Enter captcha manually: ", "BancPay", 1);

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						myElement.clear();
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement)
								// .click()
								.sendKeys(value2).sendKeys(Keys.ENTER)
								.perform();
						value1 = myElement.getAttribute("value");
						if (null != sheet.getRow(row + 1)
								|| "".equals(sheet.getRow(row + 1))) {
							final String execute1 = sheet.getRow(row + 1)
									.getCell(3).getStringCellValue();
							final String locatorType1 = sheet.getRow(row + 1)
									.getCell(5).getStringCellValue();
							final String locator1 = sheet.getRow(row + 1)
									.getCell(6).getStringCellValue();

							if (locator1 != "" && execute1.equals(testData[0])) {
								WaitCommands.waitForElement(myDriver,
										locatorType1, locator1);
							}
						}
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	/**
	 * @param myDriver
	 * @param sheet
	 * @param row
	 * @param screenshotPath
	 */
	public static void typeJS(WebDriver myDriver, HSSFSheet sheet, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.clear();
						// myElement.click();
						// myElement.sendKeys(value2);
						JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
						myJSExecutor.executeScript("arguments[0].value='"
								+ value2 + "';", myElement);
						value1 = myElement.getAttribute("value");

						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

	public static void typeJSAndWaitForNextElement(WebDriver myDriver,
			HSSFSheet sheet, int row, String screenshotPath,
			LogEntries browserLogEntries, LogEntries driverLogEntries,
			LogEntries performanceLogEntries) {

		String testData[] = CommandGlobalVariable.testData(sheet, row);
		final Cell actual = CellActions.getCell(sheet, row, 9);
		final Cell status = CellActions.getCell(sheet, row, 10);
		CellActions.setCellValueBlank(sheet, row, 10);
		final String value1;
		final String value2 = testData[10];

		df.setTimeZone(TimeZone.getTimeZone("IST"));

		try {
			if (testData[7].equals(testData[0])) {

				WebElement myElement = CommandGlobalVariable.myElement(
						myDriver, testData);

				if (myElement.isDisplayed() == true) {
					if (myElement.isEnabled() == true) {
						// JSCommands.jsScrollBy(myDriver, 0, 5);
						Actions myAction = new Actions(myDriver);
						myAction.moveToElement(myElement).perform();
						myElement.clear();
						// myElement.click();
						// myElement.sendKeys(value2);
						JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
						myJSExecutor.executeScript("arguments[0].value='"
								+ value2 + "';", myElement);
						value1 = myElement.getAttribute("value");
						if (null != sheet.getRow(row + 1)
								|| "".equals(sheet.getRow(row + 1))) {
							final String execute1 = sheet.getRow(row + 1)
									.getCell(3).getStringCellValue();
							final String locatorType1 = sheet.getRow(row + 1)
									.getCell(5).getStringCellValue();
							final String locator1 = sheet.getRow(row + 1)
									.getCell(6).getStringCellValue();

							if (locator1 != "" && execute1.equals(testData[0])) {
								WaitCommands.waitForElement(myDriver,
										locatorType1, locator1);
							}
						}
						if (value1.equals(value2)) {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case passed.");
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + "Entered value is: "
									+ value1);
							actual.setCellValue("Same as expected. \n" + value1);
							status.setCellValue("PASS");
							if ("" != testData[0] && null != testData[0]
									&& testData[5].equals(testData[0])) {
								TakeScreenshotCommands.screenshotOnPass(
										myDriver, screenshotPath, testData[6]);
							}
						} else {
							System.out.println("["
									+ new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testData[6]
									+ ": Test case failed.");
							actual.setCellValue("Test case failed. \n Actual Result: "
									+ value1);
							status.setCellValue("FAIL");
							TakeScreenshotCommands.screenshotOnFail(myDriver,
									screenshotPath, testData[6]);
						}
					} else {
						System.out.println("["
								+ new Timestamp(new Date().getTime())
								+ "] Custom Log: " + testData[6]
								+ ": Element is not enabled.");
						actual.setCellValue("Test case failed. \n Actual Result: Element is not enabled.");
						status.setCellValue("FAIL");
						TakeScreenshotCommands.screenshotOnFail(myDriver,
								screenshotPath, testData[6]);
					}
				} else {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + testData[6]
							+ ": Element is not displayed.");
					actual.setCellValue("Test case failed. \n Actual Result: Element is not displayed.");
					status.setCellValue("FAIL");
					TakeScreenshotCommands.screenshotOnFail(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
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
					testData[6]);
		} catch (NullPointerException npe) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Null pointer exception has been thrown : " + npe);
			actual.setCellValue("Null pointer exception has been thrown. \n Error details: "
					+ npe);
			status.setCellValue("FAIL");
			TakeScreenshotCommands.screenshotOnFail(myDriver, screenshotPath,
					testData[6]);
		}
	}

}
