package droom.automation.commands;

/**
 * @author Abhishek Yadav
 * 
 */

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class TakeScreenshotCommands {

	/**
	 * @param myDriver
	 * @param screenshotPath
	 * @param filename
	 */

	public static void screenshot(WebDriver myDriver, String screenshotPath,
			String filename) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		df.setTimeZone(TimeZone.getTimeZone("IST"));
		try {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: " + "Taking screenshot...");
			File scrFile = ((TakesScreenshot) myDriver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(screenshotPath + filename
						+ "-SCR-" + df.format(new Date()) + ".png"));
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "Screenshot has been saved!!");
			} catch (IOException e) {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "IO exception has been thrown : "
						+ e);
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
		}
	}

	/**
	 * @param myDriver
	 * @param screenshotPath
	 * @param testId
	 */

	public static void screenshotOnPass(WebDriver myDriver,
			String screenshotPath, String testId) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		df.setTimeZone(TimeZone.getTimeZone("IST"));
		try {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: " + "Taking screenshot on Pass...");
			File scrFile = ((TakesScreenshot) myDriver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(screenshotPath + testId
						+ "-PASS.png"));
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "Screenshot has been saved!!");
			} catch (IOException e) {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "IO exception has been thrown : "
						+ e);
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
		}
	}

	/**
	 * @param myDriver
	 * @param screenshotPath
	 * @param testId
	 */

	public static void screenshotOnFail(WebDriver myDriver,
			String screenshotPath, String testId) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		df.setTimeZone(TimeZone.getTimeZone("IST"));
		try {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: " + "Taking screenshot on Fail...");
			File scrFile = ((TakesScreenshot) myDriver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(screenshotPath + testId
						+ "-FAIL.png"));
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "Screenshot has been saved!!");
			} catch (IOException e) {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "IO exception has been thrown : "
						+ e);
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
		}
	}

	/**
	 * @param myDriver
	 * @param screenshotPath
	 * @param testId
	 */

	public static void screenshotOnError(WebDriver myDriver,
			String screenshotPath, String testId) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		df.setTimeZone(TimeZone.getTimeZone("IST"));
		try {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: " + "Taking screenshot on Error...");
			File scrFile = ((TakesScreenshot) myDriver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(screenshotPath + testId
						+ "-ERROR.png"));
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "Screenshot has been saved!!");
			} catch (IOException e) {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: " + "IO exception has been thrown : "
						+ e);
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
		}
	}

}
