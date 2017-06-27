package droom.automation.globals;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import droom.automation.excel.GetCellValue;

public class CommandGlobalVariable {

	/**
	 * @param sheet
	 * @return
	 */

	public static String[] testData(HSSFSheet sheet, int row) {

		String yes = "Yes";
		String idLocator = "id";
		String nameLocator = "name";
		String cssSelectorLocator = "css selector";
		String xpathLocator = "xpath";
		String screenshotOnPass = "";
		String testId = "";
		String execute = "";
		String locatorType = "";
		String locator = "";
		String input = "";

		try {
			screenshotOnPass = GetCellValue.getSingleCellValue(sheet, 3, 10);
			testId = GetCellValue.getSingleCellValue(sheet, row, 2);
			execute = GetCellValue.getSingleCellValue(sheet, row, 3);
			locatorType = GetCellValue.getSingleCellValue(sheet, row, 5);
			locator = GetCellValue.getSingleCellValue(sheet, row, 6);
			input = GetCellValue.getSingleCellValue(sheet, row, 7);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e);
		}

		String[] testDataVariable = { yes, idLocator, nameLocator,
				cssSelectorLocator, xpathLocator, screenshotOnPass, testId,
				execute, locatorType, locator, input };

		return testDataVariable;
	}

	/**
	 * @param myDriver
	 * @param testData
	 * @return
	 */

	public static WebElement myElement(WebDriver myDriver, String[] testData) {

		WebElement myElement = null;

		try {
			if (testData[8].equals(testData[1])) {
				myElement = myDriver.findElement(By.id(testData[9]));
			} else if (testData[8].equals(testData[2])) {
				myElement = myDriver.findElement(By.name(testData[9]));
			} else if (testData[8].equals(testData[3])) {
				myElement = myDriver.findElement(By.cssSelector(testData[9]));
			} else if (testData[8].equals(testData[4])) {
				myElement = myDriver.findElement(By.xpath(testData[9]));
			} else {
				System.out.println("Locator type is not valid!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e);
		}

		return myElement;

	}
}