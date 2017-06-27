package droom.automation.commons;

/**
 * @author Abhishek Yadav
 * 
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class GetNumberOfScenarios {

	public static List<Integer> countScenarios(HSSFSheet sheet) {

		List<Integer> scenarios = new ArrayList<Integer>();
		scenarios.add(1);

		try {
			for (Row row : sheet) {
				Cell cell = row.getCell(1);
				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
					String cellValue = cell.getStringCellValue();
					if (cellValue.contains("#")) {
						int rowCount = row.getRowNum() + 1;
						System.out.println(rowCount);
						System.out.println(cellValue);
						// System.out.println(row.getRowNum());
						scenarios.add(rowCount);
					}
				}
			}
		} catch (NullPointerException npe) {
			System.out.println("Null Pointer exception has been thrown : "
					+ npe);
		}

		return scenarios;
	}

	/*
	 * public static void main(String args[]) throws IOException {
	 * 
	 * FileInputStream inFile; HSSFWorkbook workbook; HSSFSheet sheet; inFile =
	 * new FileInputStream( new File(
	 * "C:\\AutomationTesting\\BancPay\\TestData\\BP-Invoice-RegressionTesting-English.xls"
	 * )); workbook = new HSSFWorkbook(inFile); sheet = workbook.getSheetAt(4);
	 * GetNumberOfScenarios.countScenarios(sheet); }
	 */

}
