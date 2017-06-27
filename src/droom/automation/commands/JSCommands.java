package droom.automation.commands;

/**
 * @author Abhishek Yadav
 * 
 */

import java.sql.Timestamp;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class JSCommands {

	/**
	 * @param myDriver
	 * @param x
	 * @param y
	 */

	public static void jsScrollBy(WebDriver myDriver, int x, int y) {
		try {
			if (myDriver instanceof JavascriptExecutor) {
				((JavascriptExecutor) myDriver)
						.executeScript("window.scrollBy(" + x + "," + y + ");");
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
		}
	}

	/**
	 * @param myDriver
	 * @param x
	 * @param y
	 */

	public static void jsScrollTo(WebDriver myDriver, int x, int y) {
		try {
			if (myDriver instanceof JavascriptExecutor) {
				((JavascriptExecutor) myDriver)
						.executeScript("window.scrollTo(" + x + "," + y + ");");
			}
		} catch (WebDriverException we) {
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "WebDriver exception has been thrown : " + we);
		}
	}

}
