package droom.automation.commons;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectCalendarDate {

	@SuppressWarnings("unused")
	public static void addDateEnglish(WebDriver myDriver, String fullDate) {
		WebElement dateWidget;
		List<WebElement> rows;
		List<WebElement> columns;
		List<String> list = Arrays.asList("January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December");

		String date = null;
		String calMonth = null;
		String calYear = null;
		boolean dateNotFound;
		dateNotFound = true;

		String[] parts = fullDate.split("/");
		String month = parts[0]; // 12
		date = parts[1]; // 7
		String year = parts[2]; // 2014
		int Month = Integer.parseInt(month);
		int Year = Integer.parseInt(year);

		String divmonth = "ui-datepicker-month";
		String divyear = "ui-datepicker-year";
		String Maindiv = "ui-datepicker-div";
		String next = ".//*[@id='ui-datepicker-div']/div/a[2]/span";
		String prev = ".//*[@id='ui-datepicker-div']/div/a[1]/span";
		while (dateNotFound) {

			calMonth = myDriver.findElement(By.className(divmonth)).getText();
			calYear = myDriver.findElement(By.className(divyear)).getText();
			if (list.indexOf(calMonth) + 1 == Month
					&& (Year == Integer.parseInt(calYear))) {
				dateWidget = myDriver.findElement(By.id(Maindiv));
				rows = dateWidget.findElements(By.tagName("tr"));
				columns = dateWidget.findElements(By.tagName("td"));

				for (WebElement cell : columns) {
					if (cell.getText().equals(date)) {
						cell.findElement(By.linkText(date)).click();
						break;
					}
				}
				dateNotFound = false;
			} else if (list.indexOf(calMonth) + 1 < Month
					&& (Year == Integer.parseInt(calYear))
					|| Year > Integer.parseInt(calYear)) {
				myDriver.findElement(By.xpath(next)).click();
			} else if (list.indexOf(calMonth) + 1 > Month
					&& (Year == Integer.parseInt(calYear))
					|| Year < Integer.parseInt(calYear)) {
				myDriver.findElement(By.xpath(prev)).click();
			}
		}
	}

}
