/**
 * 
 */
package com.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 22231
 *
 */
public class ShowExcelOutput {

	static ReadExcelSheet read;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String path = "C:\\Users\\22231\\git\\LatestProjectC4\\src\\main\\resources\\TestData\\ComplexExcel.xlsx";

		read = new ReadExcelSheet(path);

		read.getRowCount("Input");

		read.getDataByColName("Input", "FieldName", 1);

		for (int i = 0; i < read.getRowCount("Input"); i++) {

			int randomNum = ThreadLocalRandom.current().nextInt(1, 8 + 1);
			//read.getCellData("Input", "FieldName", randomNum);
			System.out.println(read.getDataByColName("Input", "FieldName", randomNum));
			System.out.println(read.getDataByColName("Input", "FieldName", randomNum));
			
		}

	}

}
