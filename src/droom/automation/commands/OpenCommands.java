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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;

public class OpenCommands {

	/**
	 * @param myDriver
	 * @param sheet1
	 * @param row
	 * @param screenshotPath
	 */
	public static void openURL(WebDriver myDriver, HSSFSheet sheet1, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
		final String yes = "Yes";
		final String screenshotOnPass = sheet1.getRow(3).getCell(10)
				.getStringCellValue();
		final String testId = sheet1.getRow(row).getCell(2)
				.getStringCellValue();
		final String execute = sheet1.getRow(row).getCell(3)
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
				myDriver.navigate().to(locator);
				value1 = myDriver.getCurrentUrl();

				if (value1.equals(value2)) {
					System.out
							.println("[" + new Timestamp(new Date().getTime())
									+ "] Custom Log: " + testId
									+ ": Test case passed.");
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + "Entered URL is: "
							+ myDriver.getCurrentUrl());
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

	public static void openURLAndWaitForNextElement(WebDriver myDriver,
			HSSFSheet sheet1, int row, String screenshotPath,
			LogEntries browserLogEntries, LogEntries driverLogEntries,
			LogEntries performanceLogEntries) {
		final String yes = "Yes";
		final String screenshotOnPass = sheet1.getRow(3).getCell(10)
				.getStringCellValue();
		final String testId = sheet1.getRow(row).getCell(2)
				.getStringCellValue();
		final String execute = sheet1.getRow(row).getCell(3)
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
				myDriver.navigate().to(locator);
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
							+ "] Custom Log: " + "Entered URL is: "
							+ myDriver.getCurrentUrl());
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

}
