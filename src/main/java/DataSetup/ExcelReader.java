package DataSetup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader
{
	// Reads a sheet into a list of maps
    public static List<Map<String, String>> readSheet(String resourcePath, String sheetName) {
        try (InputStream in = ExcelReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) throw new IllegalArgumentException("Excel not found on classpath: " + resourcePath);

            try (Workbook wb = new XSSFWorkbook(in)) {
                Sheet sheet = wb.getSheet(sheetName);
                if (sheet == null) throw new IllegalArgumentException("Sheet not found: " + sheetName);

                DataFormatter fmt = new DataFormatter();

                Row headerRow = sheet.getRow(sheet.getFirstRowNum());
                if (headerRow == null) return List.of();

                List<String> headers = new ArrayList<>();
                for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                    headers.add(fmt.formatCellValue(headerRow.getCell(c)).trim());
                }

                List<Map<String, String>> rows = new ArrayList<>();
                for (int r = sheet.getFirstRowNum() + 1; r <= sheet.getLastRowNum(); r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) continue;

                    Map<String, String> map = new LinkedHashMap<>();
                    boolean anyValue = false;

                    for (int c = 0; c < headers.size(); c++) {
                        String key = headers.get(c);
                        String val = fmt.formatCellValue(row.getCell(c)).trim();
                        if (!val.isEmpty()) anyValue = true;
                        map.put(key, val);
                    }

                    if (anyValue) rows.add(map);
                }
                return rows;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed reading excel: " + resourcePath + " / " + sheetName, e);
        }
    }
    public static Map<String, String> getData(String sheetName, int pageNum) {
        // 1. Path to your excel in src/test/resources
        String resourcePath = "TestData.xlsx"; 
        
        // 2. Read the entire sheet into a list of maps
        List<Map<String, String>> allRows = readSheet(resourcePath, sheetName);
        
        
        // 3. Since pageNum 1 corresponds to the first data row (List index 0)
        int rowIndex = pageNum - 1;

        if (rowIndex >= 0 && rowIndex < allRows.size()) {
            return allRows.get(rowIndex);
        } else {
            throw new RuntimeException("Page " + pageNum + " data not found in Excel! Row index: " + rowIndex);
        }
    } 

}


