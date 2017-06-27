package droom.automation.globals;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.WebDriver;

import droom.automation.commands.TakeScreenshotCommands;

public class CommandExecutionStatus {

	/**
	 * @param myDriver
	 * @param value1
	 * @param value2
	 * @param testData
	 * @param actual
	 * @param status
	 * @param screenshotPath
	 */

	public static void testCaseStatusForTextbox(WebDriver myDriver,
			String value1, String value2, String testData[], Cell actual,
			Cell status, String screenshotPath) {

		try {

			if (value1.equals(value2)) {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
						+ ": Test case passed.");
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "Entered value is: " + value1);
				actual.setCellValue("Same as expected. \n" + value1);
				status.setCellValue("PASS");
				if ("" != testData[0] && null != testData[0]
						&& testData[5].equals(testData[0])) {
					TakeScreenshotCommands.screenshotOnPass(myDriver,
							screenshotPath, testData[6]);
				}
			} else {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + testData[6]
						+ ": Test case failed.");
				actual.setCellValue("Test case failed. \n Actual Result: "
						+ value1);
				status.setCellValue("FAIL");
				TakeScreenshotCommands.screenshotOnFail(myDriver,
						screenshotPath, testData[6]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e);
		}
	}

}
