package droom.automation.commands;

/**
 * @author Abhishek Yadav
 * 
 */

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class MiscCommands {

	DateFormat df1 = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");

	/**
	 * @param myDriver
	 * @param start
	 */

	public static void refreshBrowser(WebDriver myDriver) {

		String loaded = "Page Loaded!!";
		String notLoaded = "Page did not Loaded!!";
		String refresh = "Page refreshed!!";
		long start = System.currentTimeMillis();
		long wait = 60000 * 6;

		while ((System.currentTimeMillis() - start) < wait) {
			try {
				JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
				String loading = (String) myJSExecutor
						.executeScript("var s1 = 'Page Loaded!!'; var s2 = 'Page did not Loaded!!'; if(document.readyState === 'complete'){return s1;} else {return s2;}");
				if (loading.equals(loaded)) {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + loaded);
					break;
				} else if ((System.currentTimeMillis() - start) < (wait - 60000)) {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + notLoaded);
					myDriver.navigate().refresh();
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + refresh);
					break;
				}
			} catch (WebDriverException we) {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: "
						+ "WebDriver exception has been thrown : " + we);
			}
		}
	}

	/**
	 * @param myDriver
	 * @param start
	 */

	public static void closeBrowserWhileLoading(WebDriver myDriver) {

		String loaded = "Page Loaded!!";
		String notLoaded = "Page did not Loaded!!";
		String closed = "Browser closed!!";
		long start = System.currentTimeMillis();
		long wait = 60000 * 20;

		while ((System.currentTimeMillis() - start) == wait) {
			try {
				JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
				String loading = (String) myJSExecutor
						.executeScript("var s1 = 'Page Loaded!!'; var s2 = 'Page did not Loaded!!'; if(document.readyState === 'complete'){return s1;} else {return s2;}");
				if (loading.equals(loaded)) {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + loaded);
					break;
				} else if ((System.currentTimeMillis() - start) < (wait - 60000)) {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + notLoaded);
					myDriver.close();
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + closed);
					break;
				}
			} catch (WebDriverException we) {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: "
						+ "WebDriver exception has been thrown : " + we);
			}
		}
	}

	/**
	 * @param myDriver
	 * @param start
	 */

	public static void quitBrowserWhileLoading(WebDriver myDriver) {

		String loaded = "Page Loaded!!";
		String notLoaded = "Page did not Loaded!!";
		String closed = "Driver closed!!";
		long start = System.currentTimeMillis();
		long wait = 60000 * 20;

		while ((System.currentTimeMillis() - start) == wait) {
			try {
				JavascriptExecutor myJSExecutor = (JavascriptExecutor) myDriver;
				String loading = (String) myJSExecutor
						.executeScript("var s1 = 'Page Loaded!!'; var s2 = 'Page did not Loaded!!'; if(document.readyState === 'complete'){return s1;} else {return s2;}");
				if (loading.equals(loaded)) {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + loaded);
					break;
				} else if ((System.currentTimeMillis() - start) < (wait - 60000)) {
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + notLoaded);
					myDriver.quit();
					System.out.println("["
							+ new Timestamp(new Date().getTime())
							+ "] Custom Log: " + closed);
					break;
				}
			} catch (WebDriverException we) {
				System.out.println("[" + new Timestamp(new Date().getTime())
						+ "] Custom Log: "
						+ "WebDriver exception has been thrown : " + we);
			}
		}
	}
}
