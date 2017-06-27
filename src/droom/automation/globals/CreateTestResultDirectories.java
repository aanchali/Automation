package droom.automation.globals;

 

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CreateTestResultDirectories {

	/**
	 * @param rootPath
	 * @param automationPath
	 * @param appPath
	 * @param testingPath
	 * @param testingDatePath
	 * @param logPath
	 * @param reportPath
	 * @param screenshotPath
	 */

	public static void main(String rootPath, String automationPath,
			String appPath, String testingPath, String testingDatePath,
			String logPath, String reportPath, String screenshotPath) {

		DateFormat df1 = new SimpleDateFormat("hh:mm:ss");
		df1.setTimeZone(TimeZone.getTimeZone("IST"));

		File rootDirectory = new File(rootPath);
		if (!rootDirectory.exists()) {
			try {
				rootDirectory.mkdir();
			} catch (SecurityException secEx) {
				secEx.printStackTrace(System.out);
				System.out.println("Security exception has been thrown : "
						+ secEx);
				rootDirectory = null;
			}
		}

		File automationDirectory = new File(automationPath);
		if (!automationDirectory.exists()) {
			try {
				automationDirectory.mkdir();
			} catch (SecurityException secEx) {
				secEx.printStackTrace(System.out);
				System.out.println("Security exception has been thrown : "
						+ secEx);
				automationDirectory = null;
			}
		}

		File appDirectory = new File(appPath);
		if (!appDirectory.exists()) {
			try {
				appDirectory.mkdir();
			} catch (SecurityException secEx) {
				secEx.printStackTrace(System.out);
				System.out.println("Security exception has been thrown : "
						+ secEx);
				appDirectory = null;
			}
		}

		File testingDirectory = new File(testingPath);
		if (!testingDirectory.exists()) {
			try {
				testingDirectory.mkdir();
			} catch (SecurityException secEx) {
				secEx.printStackTrace(System.out);
				System.out.println("Security exception has been thrown : "
						+ secEx);
				testingDirectory = null;
			}
		}

		File testingDateDirectory = new File(testingDatePath);
		if (!testingDateDirectory.exists()) {
			try {
				testingDateDirectory.mkdir();
			} catch (SecurityException secEx) {
				secEx.printStackTrace(System.out);
				System.out.println("Security exception has been thrown : "
						+ secEx);
				testingDateDirectory = null;
			}
		}/*
		 * else { File newTestingDateDirectory = new File(testingDateDirectory +
		 * "_RenamedAt_" + df1.format(new Date())); try {
		 * testingDateDirectory.renameTo(newTestingDateDirectory);
		 * testingDateDirectory.mkdir(); } catch (SecurityException secEx) {
		 * secEx.printStackTrace(System.out);
		 * System.out.println("Security exception has been thrown : " + secEx);
		 * automationDirectory = null; } }
		 */

		File logDirectory = new File(logPath);
		if (!logDirectory.exists()) {
			try {
				logDirectory.mkdir();
			} catch (SecurityException secEx) {
				secEx.printStackTrace(System.out);
				System.out.println("Security exception has been thrown : "
						+ secEx);
				logDirectory = null;
			}
		}

		File reportDirectory = new File(reportPath);
		if (!reportDirectory.exists()) {
			try {
				reportDirectory.mkdir();
			} catch (SecurityException secEx) {
				secEx.printStackTrace(System.out);
				System.out.println("Security exception has been thrown : "
						+ secEx);
				reportDirectory = null;
			}
		}

		File screenshotDirectory = new File(screenshotPath);
		if (!screenshotDirectory.exists()) {
			try {
				screenshotDirectory.mkdir();
			} catch (SecurityException secEx) {
				secEx.printStackTrace(System.out);
				System.out.println("Security exception has been thrown : "
						+ secEx);
				screenshotDirectory = null;
			}
		}
	}
}
