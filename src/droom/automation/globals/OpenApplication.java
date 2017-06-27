package droom.automation.globals;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;

import droom.automation.commands.OpenCommands;

public class OpenApplication {
	public static void openURL(WebDriver myDriver, HSSFSheet sheet, int row,
			String screenshotPath, LogEntries browserLogEntries,
			LogEntries driverLogEntries, LogEntries performanceLogEntries)
	{
		System.out.println("[" + new Timestamp(new Date().getTime())
				+ "] Custom Log: " + "Application is getting opened...");
		OpenCommands.openURL(myDriver, sheet, row, screenshotPath,
				browserLogEntries, driverLogEntries, performanceLogEntries);
		System.out.println("[" + new Timestamp(new Date().getTime())
				+ "] Custom Log: " + "Application has been opened!!");
		System.out.println("");
	}
}


