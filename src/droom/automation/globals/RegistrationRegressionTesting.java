package droom.automation.globals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import droom.automation.globals.CreateReportDirectories;
import droom.automation.globals.RegressionTesting;

public class RegistrationRegressionTesting {

	public static void main(String[] args) {

		// Environment setup starts
		String setEnv = "vmAutomation";
		// Environment setup ends

		// Path setup starts
		String directories[] = CreateReportDirectories
				.createDirectories(setEnv);
		// Path setup ends

		// File setup starts
		String testDataFileName = "droom_details.xls";
		String excelReportFileName = "droom_details-Result.xls";
		String logFileName = "Registration-Log.log";
		// File setup ends

		try {

			// log setup starts
			File logFile = new File(directories[6] + logFileName);
			System.setOut(new PrintStream(new FileOutputStream(logFile)));
			// log setup ends

			// WebDriver setup starts
			WebDriver myDriver = null;
			// WebDriver setup ends

			// Excel setup starts
			FileInputStream file;
			FileOutputStream outFile;
			HSSFWorkbook workbook;
			HSSFSheet sheet;

			int sheetCount[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

			// Initializing test data file
			file = new FileInputStream(new File(directories[9]
					+ testDataFileName));
			workbook = new HSSFWorkbook(file);

			// Saving excel report file starts
			outFile = new FileOutputStream(new File(directories[7]
					+ excelReportFileName));
			workbook.write(outFile);
			outFile.close();
			// Saving excel report file ends

			// Registration module test execution starts

			System.out
					.println("-----------------------------------------------------------------------------");
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Test execution for registration started...");
			System.out
					.println("-----------------------------------------------------------------------------");
			System.out.println("");

			sheet = workbook.getSheetAt(sheetCount[1]);
			RegressionTesting.signUpRegressionTest(myDriver, workbook, sheet,
					directories[11], directories[10], directories[8], outFile,
					directories[7], excelReportFileName);

			/*sheet = workbook.getSheetAt(sheetCount[2]);
			RegressionTesting.signUpRegressionTest(myDriver, workbook, sheet,
					directories[11], directories[10], directories[8], outFile,
					directories[7], excelReportFileName);

			sheet = workbook.getSheetAt(sheetCount[3]);
			RegressionTesting.signUpRegressionTest(myDriver, workbook, sheet,
					directories[11], directories[10], directories[8], outFile,
					directories[7], excelReportFileName);

			sheet = workbook.getSheetAt(sheetCount[4]);
			RegressionTesting.MyAccountRegressionTest(myDriver, workbook,
					sheet, directories[11], directories[10], directories[8],
					outFile, directories[7], excelReportFileName);

			sheet = workbook.getSheetAt(sheetCount[5]);
			RegressionTesting.MyAccountRegressionTest(myDriver, workbook,
					sheet, directories[11], directories[10], directories[8],
					outFile, directories[7], excelReportFileName);

			sheet = workbook.getSheetAt(sheetCount[6]);
			RegressionTesting.MyAccountRegressionTest(myDriver, workbook,
					sheet, directories[11], directories[10], directories[8],
					outFile, directories[7], excelReportFileName);

			sheet = workbook.getSheetAt(sheetCount[7]);
			RegressionTesting.usersRegression(myDriver, workbook, sheet,
					directories[11], directories[10], directories[8], outFile,
					directories[7], excelReportFileName);

			sheet = workbook.getSheetAt(sheetCount[8]);
			RegressionTesting.usersRegression(myDriver, workbook, sheet,
					directories[11], directories[10], directories[8], outFile,
					directories[7], excelReportFileName);

			sheet = workbook.getSheetAt(sheetCount[9]);
			RegressionTesting.usersRegression(myDriver, workbook, sheet,
					directories[11], directories[10], directories[8], outFile,
					directories[7], excelReportFileName);

			sheet = workbook.getSheetAt(sheetCount[10]);
			RegressionTesting.adminRegression(myDriver, workbook, sheet,
					directories[11], directories[10], directories[8], outFile,
					directories[7], excelReportFileName);
*/
			System.out
					.println("-----------------------------------------------------------------------------");
			System.out.println("[" + new Timestamp(new Date().getTime())
					+ "] Custom Log: "
					+ "Test execution for registration ended.");
			System.out
					.println("-----------------------------------------------------------------------------");
			System.out.println("");
			file.close();
			// Registration module test execution ends

			// Saving excel report file starts
			outFile = new FileOutputStream(new File(directories[7]
					+ excelReportFileName));
			workbook.write(outFile);
			outFile.close();
			// Saving excel report file ends

		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found exception: " + fnfe);
		} catch (IOException ioe) {
			System.out.println("IO exception: " + ioe);
		}
	}

}
