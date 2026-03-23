//package pages;
//
//import io.restassured.response.Response;
//import org.apache.poi.xssf.usermodel.*;
//import org.testng.annotations.*;
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//import static io.restassured.RestAssured.given;
//
//public class ExcelUtils {
//    private String path = System.getProperty("user.dir") + "/src/test/resources/TestData.xlsx";
//    private XSSFWorkbook workbook;
//    private XSSFSheet sheet;
//    private FileInputStream fis;
//
//    @Test(dataProvider = "apiData")
//    public void testPostAndSaveResponse(String name, String job, int rowNum) throws IOException {
//        // 1. Prepare Request Body
//        Map<String, String> body = new HashMap<>();
//        body.put("name", name);
//        body.put("job", job);
//
//        // 2. Send POST request to the correct endpoint
//        Response response = given()
//            .contentType("application/json")
//            .body(body)
//        .when()
//            .post("https://reqres.in/api/users");
//
//        // 3. Extract Response Data
//        String generatedId = response.jsonPath().getString("id");
//        int statusCode = response.getStatusCode();
//
//        // 4. Write results back to the SHARED workbook object
//        synchronized(workbook) { 
//            XSSFRow row = sheet.getRow(rowNum);
//            if (row == null) row = sheet.createRow(rowNum);
//            
//            // Column C (Index 2) for ID, Column D (Index 3) for Status
//            row.createCell(2).setCellValue(generatedId);
//            row.createCell(3).setCellValue(String.valueOf(statusCode));
//
//            // Save changes to the file immediately
//            try (FileOutputStream fos = new FileOutputStream(path)) {
//                workbook.write(fos);
//                fos.flush();
//            }
//        }
//    }
//
//    @DataProvider(name = "apiData")
//    public Object[][] getTestData() throws IOException {
//        fis = new FileInputStream(path);
//        workbook = new XSSFWorkbook(fis);
//        sheet = workbook.getSheetAt(0);
//        
//        int totalRows = sheet.getLastRowNum(); 
//        Object[][] data = new Object[totalRows][3]; 
//        
//        for (int i = 1; i <= totalRows; i++) {
//            XSSFRow row = sheet.getRow(i);
//            data[i-1][0] = row.getCell(0).getStringCellValue(); // Name
//            data[i-1][1] = row.getCell(1).getStringCellValue(); // Job
//            data[i-1][2] = i; // Pass row index
//        }
//        return data;
//    }
//
//    @AfterClass
//    public void tearDown() throws IOException {
//        if (fis != null) fis.close();
//        if (workbook != null) workbook.close();
//    }
//}
