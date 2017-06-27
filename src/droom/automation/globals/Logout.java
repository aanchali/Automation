package droom.automation.globals;

/**
 * @author Abhishek Yadav
 * 
 */

import java.sql.Timestamp;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;

import droom.automation.commands.ClickCommands;
import droom.automation.commands.VerifyCommands;

public class Logout {
	/**
	 * @param myDriver
	 * @param sheet
	 * @param row
	 * @param screenshotPath
	 * @throws InterruptedException
	 */

	public static void logout(WebDriver myDriver, HSSFSheet sheet, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries)
			throws InterruptedException {
		System.out.println("[" + new Timestamp(new Date().getTime())
				+ "] Custom Log: "
				+ "User is getting logged out from the application...");
		ClickCommands.clickLink(myDriver, sheet, row,
				screenshotPath, browserLogEntries, driverLogEntries,
				performanceLogEntries);
		Thread.sleep(5000);
		VerifyCommands.verifyElementPresent(myDriver, sheet, row = row + 1,
				screenshotPath, browserLogEntries, driverLogEntries,
				performanceLogEntries);
		System.out.println("[" + new Timestamp(new Date().getTime())
				+ "] Custom Log: "
				+ "User has been logged out from the application!!");
		System.out.println("");
	}

	/**
	 * @param myDriver
	 * @param sheet
	 * @param row
	 * @param screenshotPath
	 */

	public static void logoutFromSettings(WebDriver myDriver, HSSFSheet sheet,
			int row, String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries) {
		System.out.println("[" + new Timestamp(new Date().getTime())
				+ "] Custom Log: "
				+ "User is getting logged out from the application...");
		ClickCommands.clickImageAndWaitForNextElement(myDriver, sheet, row,
				screenshotPath, browserLogEntries, driverLogEntries,
				performanceLogEntries);
		ClickCommands.clickLinkAndWaitForNextElement(myDriver, sheet,
				row = row + 1, screenshotPath, browserLogEntries,
				driverLogEntries, performanceLogEntries);
		VerifyCommands.verifyElementPresentAndWaitForNextElement(myDriver,
				sheet, row = row + 1, screenshotPath, browserLogEntries,
				driverLogEntries, performanceLogEntries);
		System.out.println("[" + new Timestamp(new Date().getTime())
				+ "] Custom Log: "
				+ "User has been logged out from the application!!");
		System.out.println("");
	}
}
