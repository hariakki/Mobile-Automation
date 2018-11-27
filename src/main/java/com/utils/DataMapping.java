/**
 * 
 */
package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author 22231
 *
 */
public class DataMapping {

	
	private void prepareTestDataMapping() {
        XSSFWorkbook testDataBook = null;
        FileInputStream testDataStream = null;
        try {
               File testDataFile = new File("C:\\Automation\\SpiceJetPOC\\TestData\\ScriptDataSheetMapping1.xlsx");
               testDataStream = new FileInputStream(testDataFile);
               testDataBook = new XSSFWorkbook(testDataStream);
               XSSFSheet testDataSheet = testDataBook.getSheet(("ScriptDataSheetMapping"));

               for (int rowCounter = 0; rowCounter <= testDataSheet.getLastRowNum(); rowCounter++) {
                     XSSFRow currentRow = testDataSheet.getRow(rowCounter);
                     HashMap<String, String> testDataDetails = new HashMap<String, String>();
                     testDataDetails.put("DataFile", currentRow.getCell(1).getStringCellValue().trim());
                     testDataDetails.put("DataSheet", currentRow.getCell(2).getStringCellValue().trim());
                    // testDataMapping.put(currentRow.getCell(0).getStringCellValue().trim(), testDataDetails);
               }
               //testDataBook.close();
               testDataStream.close();
        } catch (IOException e) {
               throw new NullPointerException("Error in reading spreadsheet");
        }
 }

	
}
