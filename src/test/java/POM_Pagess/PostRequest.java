package POM_Pagess;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map; // ADD THIS IMPORT

import org.apache.poi.xssf.usermodel.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class PostRequest {
    private String path = System.getProperty("user.dir") + "/src/test/resources/TestData.xlsx";
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private FileInputStream fis;

    
    public Map<String, String> getDataByRow(int rowNum) throws IOException {
        if (workbook == null) {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
        }
        
        Map<String, String> data = new HashMap<>();
        XSSFRow row = sheet.getRow(rowNum);
        
        if (row != null) {
            // Assuming Col 0 = name, Col 1 = job (adjust if your Excel is different)
            data.put("name", row.getCell(0).getStringCellValue());
            data.put("job", row.getCell(1).getStringCellValue());
           // data.put("email", emailPrefix + "_" + System.currentTimeMillis() + "@example.com");
            
        }
        return data;
    }

    @DataProvider(name = "apiData")
    public Object[][] getTestData() throws IOException {
        if (workbook == null) {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
        }
        int totalRows = sheet.getLastRowNum();
        Object[][] data = new Object[totalRows][5];
        for (int i = 1; i <= totalRows; i++) {
            data[i-1][0] = sheet.getRow(i).getCell(0).getStringCellValue();
            data[i-1][1] = sheet.getRow(i).getCell(1).getStringCellValue();
                       
            data[i-1][2] = i; 
            

        }
        return data;
    }

    public void writeSpecificRow(int rowNum, String id, int status, String fullResponse) throws IOException {
        // loadWorkbook(); // Ensure workbook is loaded
         
         XSSFRow row = sheet.getRow(rowNum);
         if (row == null) row = sheet.createRow(rowNum);
         
         // Existing columns logic
         row.createCell(2).setCellValue(id == null ? "N/A" : id);
         row.createCell(3).setCellValue(status);
         
         // NEW: Store complete response in Column 4 (Index 4)
         if (fullResponse != null) {
             XSSFCell cell4 = row.createCell(4);
             
             // Excel limit is 32,767 characters per cell
             if (fullResponse.length() > 32760) {
                 fullResponse = fullResponse.substring(0, 32760) + "...[TRUNCATED]";
             }
             cell4.setCellValue(fullResponse);
             
             // Optional: Make it readable with Wrap Text
             XSSFCellStyle style = workbook.createCellStyle();
             style.setWrapText(true);
             cell4.setCellStyle(style);
         }

         // Atomic Write to avoid file corruption
         try (FileOutputStream fos = new FileOutputStream(path)) {
             workbook.write(fos);
             fos.flush();
         }
     }
}
