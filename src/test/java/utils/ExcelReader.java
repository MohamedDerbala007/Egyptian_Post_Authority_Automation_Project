package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    // Method to read data from Excel
    public static Object[][] readDataFromExcel(String excelFilePath, String sheetName) {
        Object[][] data = null;
        try {
            // Create a FileInputStream to read the Excel file
            FileInputStream file = new FileInputStream(new File(excelFilePath));

            // Create a Workbook instance
            Workbook workbook = new XSSFWorkbook(file);

            // Get the specified sheet by name
            Sheet sheet = workbook.getSheet(sheetName);
            
            // Get the number of rows and columns
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            // Create a 2D array to hold the data
            data = new Object[rowCount - 1][colCount];  // -1 to skip the header row

            // Loop through the rows and columns
            for (int i = 1; i < rowCount; i++) {  // Starting from 1 to skip header
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    data[i - 1][j] = row.getCell(j).toString();  // Store cell data in array
                }
            }

            // Close the workbook
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
