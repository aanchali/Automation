package droom.automation.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CellActions {

	/**
	 * @param sheet
	 * @param rowNum
	 * @param cellNum
	 */

	public static Cell getCell(HSSFSheet sheet, int rowNum, int cellNum) {

		Row row = null;
		Cell cell = null;

		try {

			row = sheet.getRow(rowNum);
			cell = row.getCell(cellNum);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e);
		}

		return cell;
	}

	/**
	 * @param sheet
	 * @param rowNum
	 * @param cellNum
	 */

	public static void setCellValueBlank(HSSFSheet sheet, int rowNum,
			int cellNum) {

		try {

			Row row = sheet.getRow(rowNum);
			Cell cell = row.getCell(cellNum);

			if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
				cell.setCellType(Cell.CELL_TYPE_BLANK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e);
		}
	}
}
