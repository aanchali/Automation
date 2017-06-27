package droom.automation.globals;



import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import bsh.Console;
import droom.automation.commands.ClickCommands;
import droom.automation.commands.GenericCommands;
import droom.automation.commands.GetCommands;
import droom.automation.commands.SelectCommands;
import droom.automation.commands.TypeCommands;
import droom.automation.commands.VerifyCommands;
import droom.automation.commands.WindowCommands;
import droom.automation.commons.MiscCommons;
import droom.automation.globals.Logout;
import droom.automation.globals.OpenApplication;

public class SignUpRegressionTesting {

	/**
	 * 
	 * @param sheet
	 * @param browser
	 * @param driverPath
	 * @param screenshotPath
	 */

	public static void main(WebDriver myDriver, HSSFSheet sheet,
			String browser, String profilePath, String driverPath,
			String screenshotPath) {

		final String yes = "Yes";
		final String no = "No";
		final String na = "NA";
		String naCellValue;
		String executeTestScenario;
		String locatorType;
		String locator;
		String firefox = "firefox";
		String chrome = "chrome";
		String ie = "iexplore";
		String activationLink = null;
		int[] rows = {1, 9, 13, 21, 26};
		int row = rows[0];
		int row1 = rows[1];
		final String executeTestSuite = sheet.getRow(row).getCell(10).getStringCellValue();

		String chromeDriver = "chromedriver32.exe";
		String ieDriver = "IEDriverServer32.exe";
		
		DesiredCapabilities capabilities;
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.ALL);
		logs.enable(LogType.DRIVER, Level.ALL);
		logs.enable(LogType.PERFORMANCE, Level.ALL);

		Logs seleniumLogs = null;
		LogEntries browserLogEntries = null;
		LogEntries driverLogEntries = null;
		LogEntries performanceLogEntries = null;

		MiscCommons testRun = new MiscCommons();
		Boolean runNextTest = false;

		try {

			if (yes.equals(executeTestSuite)) {

				if (browser.equals(firefox)) 
				{
					/*
					 * FirefoxProfile firefoxProfile = new FirefoxProfile(new
					 * File(profilePath)); myDriver = new
					 * FirefoxDriver(firefoxProfile);
					 */
					capabilities = DesiredCapabilities.firefox();
					capabilities.setCapability(CapabilityType.LOGGING_PREFS,
							logs);
					myDriver = new FirefoxDriver(capabilities);

					seleniumLogs = myDriver.manage().logs();
					browserLogEntries = seleniumLogs.get(LogType.BROWSER);
					driverLogEntries = seleniumLogs.get(LogType.DRIVER);
					performanceLogEntries = seleniumLogs
							.get(LogType.PERFORMANCE);
				}
				else if (browser.equals(chrome)) 
				{
					File driver = new File(driverPath + chromeDriver);
					System.setProperty("webdriver.chrome.driver",
							driver.getAbsolutePath());
					capabilities = DesiredCapabilities.chrome();
					capabilities.setCapability(CapabilityType.LOGGING_PREFS,
							logs);
					myDriver = new ChromeDriver(capabilities);

					seleniumLogs = myDriver.manage().logs();
					browserLogEntries = seleniumLogs.get(LogType.BROWSER);
					driverLogEntries = seleniumLogs.get(LogType.DRIVER);
					performanceLogEntries = seleniumLogs
							.get(LogType.PERFORMANCE);
				} 
				else if (browser.equals(ie)) {
					File driver = new File(driverPath + ieDriver);
					System.setProperty("webdriver.ie.driver",
							driver.getAbsolutePath());
					capabilities = DesiredCapabilities.internetExplorer();
					capabilities
							.setCapability(
									InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
									true);
					capabilities.setCapability(CapabilityType.LOGGING_PREFS,
							logs);
					myDriver = new InternetExplorerDriver(capabilities);
				}

				myDriver.manage().window().maximize();
				myDriver.manage().timeouts()
						.pageLoadTimeout(60, TimeUnit.SECONDS);
				myDriver.manage().timeouts()
						.setScriptTimeout(60, TimeUnit.SECONDS);

				System.out
						.println("-----------------------------------------------------------------------------");
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: "
						+ "Test execution started for registration on "
						+ browser);
				System.out
						.println("-----------------------------------------------------------------------------");
				System.out.println("");
			row = rows[1];
				executeTestScenario = sheet.getRow(row - 2).getCell(3)
						.getStringCellValue();
				if (yes.equals(executeTestScenario)) 
				{
					OpenApplication.openURL(myDriver, sheet, row,
							screenshotPath, browserLogEntries,
							driverLogEntries, performanceLogEntries);
				}

		row = rows[2];
				//row1 = rows[3];
				executeTestScenario = sheet.getRow(row - 2).getCell(3)
						.getStringCellValue();
				locatorType = sheet.getRow(row1 - 4).getCell(5)
						.getStringCellValue();
				locator = sheet.getRow(row1 - 4).getCell(6)
						.getStringCellValue();
				if (yes.equals(executeTestScenario)) {
								
					ClickCommands.clickLink(myDriver,
							sheet, row , screenshotPath,
							browserLogEntries, driverLogEntries,
							performanceLogEntries);
					
		/*			ClickCommands.clickLink(myDriver,
							sheet, row = row + 1, screenshotPath,
							browserLogEntries, driverLogEntries,
							performanceLogEntries);
					
					TypeCommands.type(myDriver,
							sheet, row = row + 1, screenshotPath,
							browserLogEntries, driverLogEntries,
							performanceLogEntries);
					
					TypeCommands.type(myDriver,
							sheet, row = row + 1, screenshotPath,
							browserLogEntries, driverLogEntries,
							performanceLogEntries);
					
					ClickCommands.clickLink(myDriver,
							sheet, row = row + 1, screenshotPath,
							browserLogEntries, driverLogEntries,
							performanceLogEntries);
					//runNextTest = testRun.nextScenario(myDriver, locatorType,
							//locator); */
				}
				
//For Closed application
	/*			row = rows[3];
				executeTestScenario = sheet.getRow(row - 2).getCell(3)
						.getStringCellValue();
				locatorType = sheet.getRow(row1 - 4).getCell(5)
						.getStringCellValue();
				locator = sheet.getRow(row1 - 4).getCell(6)
						.getStringCellValue();
				if (yes.equals(executeTestScenario)) {		
					//click logout button
					
					ClickCommands.clickLink(myDriver,
							sheet, row , screenshotPath,
							browserLogEntries, driverLogEntries,
							performanceLogEntries);
					
					ClickCommands.clickLink(myDriver,
							sheet, row = row + 1, screenshotPath,
							browserLogEntries, driverLogEntries,
							performanceLogEntries);
					
					runNextTest = testRun.nextScenario(myDriver, locatorType,
							locator);
				            }
	*/		
	    	
		row=rows[4];
		executeTestScenario = sheet.getRow(row - 2).getCell(3)
				.getStringCellValue();
		locatorType = sheet.getRow(row1 - 4).getCell(5)
				.getStringCellValue();
		locator = sheet.getRow(row1 - 4).getCell(6)
				.getStringCellValue();
		if (yes.equals(executeTestScenario)) 
		{								
		
			ClickCommands.clickLink(myDriver,
					sheet, row , screenshotPath,
					browserLogEntries, driverLogEntries,
					performanceLogEntries);
			
             TypeCommands.type(myDriver,
					sheet, row = row + 1, screenshotPath,
					browserLogEntries, driverLogEntries,
					performanceLogEntries);
		
	       	TypeCommands.type(myDriver,
					sheet, row = row + 1, screenshotPath,
					browserLogEntries, driverLogEntries,
					performanceLogEntries);

	    	TypeCommands.type(myDriver,
					sheet, row = row + 1, screenshotPath,
					browserLogEntries, driverLogEntries,
					performanceLogEntries);
	    	
	    	TypeCommands.type(myDriver,
					sheet, row = row + 1, screenshotPath,
					browserLogEntries, driverLogEntries,
					performanceLogEntries);
	    	
	    	TypeCommands.type(myDriver,
					sheet, row = row + 1, screenshotPath,
					browserLogEntries, driverLogEntries,
					performanceLogEntries);
	    	
	    	TypeCommands.type(myDriver,
					sheet, row = row + 1, screenshotPath,
					browserLogEntries, driverLogEntries,
					performanceLogEntries);
	    	
			runNextTest = testRun.nextScenario(myDriver, locatorType,
					locator);
			
		  }
				myDriver.quit();

				System.out
						.println("-----------------------------------------------------------------------------");

				System.out
						.println("[" + new Timestamp(new Date().getTime())
								+ "] Custom Log: "
								+ "Test execution ended for Registartion on "
								+ browser);

				System.out
						.println("-----------------------------------------------------------------------------");
				System.out.println("");
			}
		} 
	
		catch (Exception ie1)
		{
			ie1.printStackTrace();
		}

	}
}
