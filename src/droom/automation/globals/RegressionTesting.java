package droom.automation.globals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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

public class RegressionTesting {
	static DateFormat df1 = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");

	/**
	 * @param myDriver
	 * @param workbook
	 * @param sheet
	 * @param profilePath
	 * @param driverPath
	 * @param screenshotPath
	 * @param outFile
	 * @param reportPath
	 * @param reportFileName
	 */

	public static void signUpRegressionTest(WebDriver myDriver,
			HSSFWorkbook workbook, HSSFSheet sheet, String profilePath,
			String driverPath, String screenshotPath, FileOutputStream outFile,
			String reportPath, String reportFileName) {

		df1.setTimeZone(TimeZone.getTimeZone("IST"));

		Cell start = sheet.getRow(5).getCell(4);
		Cell end = sheet.getRow(5).getCell(7);

		String browser = sheet.getRow(3).getCell(7).getStringCellValue();

		String startTime = df1.format(new Date()).toString();
		start.setCellType(Cell.CELL_TYPE_BLANK);
		start.setCellValue(startTime);

		SignUpRegressionTesting.main(myDriver, sheet, browser, profilePath,
				driverPath, screenshotPath);

		String endTime = df1.format(new Date()).toString();
		end.setCellType(Cell.CELL_TYPE_BLANK);
		end.setCellValue(endTime);

		// Saving excel report file starts
		try 
		{
		outFile = new FileOutputStream(new File(reportPath + reportFileName));
		workbook.write(outFile);
		outFile.close();
		} 
		catch (FileNotFoundException fnfe)
		{
		System.out.println("File not found exception: " + fnfe);
		} 
		catch (IOException ioe) 
		{
			System.out.println("IO exception: " + ioe);
		}
		// Saving excel report file ends
	}
}
	/**
	 * @param myDriver
	 * @param workbook
	 * @param sheet
	 * @param profilePath
	 * @param driverPath
	 * @param screenshotPath
	 * @param outFile
	 * @param reportPath
	 * @param reportFileName
	 */
	
