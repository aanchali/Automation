package droom.automation.globals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import droom.automation.globals.CreateReportDirectories;

public class CreateReportDirectories {

	public static String[] createDirectories(String setEnv) {

		// Date and number format setup starts
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setTimeZone(TimeZone.getTimeZone("IST"));
		// Date and number format setup ends

		// environments
		String env[] = { "local", "vmAutomation", "vmCcnet", "vmCcnetTest" };

		// Path setup starts
		String rootPath;
		if (setEnv.equals(env[2]) || setEnv.equals(env[3])) {
			rootPath = "C:/test results/";
		} else {
			rootPath = "C:/test results/";
		}
		String automationPath = rootPath + "TestAutomation/";
		String appPath = automationPath + "Droom/";
		String testingPath = appPath + "RegressionTesting/";
		String testingDatePath = testingPath + df.format(new Date()) + "/";
		String logPath = testingDatePath + "logs/";
		String reportPath = testingDatePath + "reports/";
		String screenshotPath = testingDatePath + "screenshots/";

		// creating path folders
		CreateTestResultDirectories.main(rootPath, automationPath, appPath,
				testingPath, testingDatePath, logPath, reportPath,
				screenshotPath);

		String testDataPath;
		String driverPath;
		String profilePath;
		if (setEnv.equals(env[2]) || setEnv.equals(env[3])) {
			testDataPath = "C:\\Users\\Aanchali\\workspace\\DroomAuto_Key\\test data\\";
			driverPath = "C:\\Users\\Aanchali\\workspace\\DroomAuto_Key\\drivers\\";
			profilePath = "C:\\Users\\Aanchali\\workspace\\DroomAuto_Key\\Profiles\\";
		} else {
			testDataPath = "C:\\Users\\Aanchali\\workspace\\DroomAuto_Key\\test data\\";
			driverPath = "C:\\Users\\Aanchali\\workspace\\DroomAuto_Key\\drivers\\";
			profilePath = "C:\\Users\\Aanchali\\workspace\\DroomAuto_Key\\Profiles\\";
		}
		// Path setup ends

		String testDirectories[] = { setEnv, rootPath, automationPath, appPath,
				testingPath, testingDatePath, logPath, reportPath,
				screenshotPath, testDataPath, driverPath, profilePath };

		// File setup starts
		String registrationExcelReportFileName = "droom_details-Result.xls";
		String connectionExcelReportFileName = "BP-Invoice-RegressionTesting-English-Result.xls";
		String invoiceExcelReportFileName = "BP-Invoice-RegressionTesting-English-Result.xls";
		String htmlReportFileName = "BP-RegressionTesting-English-Result.html";

		File registrationExcelReportFile = new File(testDirectories[7]
				+ registrationExcelReportFileName);
		File connectionExcelReportFile = new File(testDirectories[7]
				+ connectionExcelReportFileName);
		File invoiceExcelReportFile = new File(testDirectories[7]
				+ invoiceExcelReportFileName);
		File htmlReportFile = new File(testDirectories[7] + htmlReportFileName);
		// File setup ends

		// Saving html report file starts
		FileWriter fWriter = null;
		BufferedWriter writer = null;
		try {
			if (!registrationExcelReportFile.exists()) {
				fWriter = new FileWriter(invoiceExcelReportFile);
			}
			if (!connectionExcelReportFile.exists()) {
				fWriter = new FileWriter(connectionExcelReportFile);
			}
			if (!invoiceExcelReportFile.exists()) {
				fWriter = new FileWriter(invoiceExcelReportFile);
			}
			if (!htmlReportFile.exists()) {
				fWriter = new FileWriter(htmlReportFile);
				writer = new BufferedWriter(fWriter);
				writer.write("<span>HTML Report has been not prepared because of some error. <br /> Please see the log for error details.</span>");
				writer.newLine();
				writer.close();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		// Saving html report file ends

		return testDirectories;

	}
}
