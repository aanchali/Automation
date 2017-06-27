package droom.automation.excel;

/**
 * @author Abhishek Yadav
 * 
 */

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class RemoveWorksheets {

	/**
	 * @param workbook
	 * @param from
	 * @param to
	 */

	public static void main(HSSFWorkbook workbook, int from, int to) {

		try {
			int i;
			for (i = to; i >= from; i--) {
				workbook.removeSheetAt(i);
			}
		} catch (Exception e) {
			System.out.println("Exception has been thrown : " + e);
		}
	}
}
