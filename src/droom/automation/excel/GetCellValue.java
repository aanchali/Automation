package droom.automation.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class GetCellValue {

	/**
	 * @param sheet
	 * @param rowNum
	 * @param cellNum
	 * @return
	 */

	public static String getSingleCellValue(HSSFSheet sheet, int rowNum,
			int cellNum) {

		String cellValue = "";

		try {

			Row row = sheet.getRow(rowNum);
			Cell cell = row.getCell(cellNum);

			if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {

				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					int intCellValue = (int) cell.getNumericCellValue();
					cellValue = Integer.toString(intCellValue);
				} else {
					cellValue = cell.getStringCellValue();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e);
		}

		return cellValue;
	}

}
