package Spring.LoginRegister.Admin;



import Spring.LoginRegister.Entity.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


public class Helper {
    //check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }

    }
    public  static List<Product> convertExcelToListOfHousesProduct(InputStream is) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LoginRegister", "devuser", "Hazard93@");

            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet Housesheet = workbook.getSheet("houseList");
            XSSFSheet productsheet = workbook.getSheet("data");
            System.out.println("sheet1:" + Housesheet);

            System.out.println("sheet2:" + productsheet);
            Map<String, List<String>> houseMap = new HashMap<String, List<String>>();
            Iterator<Row> houseRowIterator = Housesheet.iterator();
            while (houseRowIterator.hasNext()) {
                Row row = houseRowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                List<String> rowValues = new ArrayList<>();
                int cellIdx = 0;

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cellIdx == 0) {
                        cellIdx++;
                        continue;
                    }



                        rowValues.add(String.valueOf(cell.getStringCellValue()));
                            System.out.println("house:" + String.valueOf(cell.getStringCellValue()));


                }
                houseMap.put(rowValues.get(0), rowValues);
            }
            List<List<String>> productList = new ArrayList<List<String>>();
            Iterator<Row> productRowIterator = productsheet.iterator();
            while (productRowIterator.hasNext()) {
                Row row = productRowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                List<String> rowValues = new ArrayList<String>();
                int cellIdx=0;
                while (cellIterator.hasNext()) {

                    if (cellIdx == 0) {
                        cellIdx++;
                        continue;
                    }
                    Cell cell = cellIterator.next();
                    rowValues.add(cell.getStringCellValue());
                    System.out.println("product:" + cell.getStringCellValue());
                }
                productList.add(rowValues);
            }
            for (List<String> productRowValues : productList) {
                List<String> houseRowValues = houseMap.get(productRowValues.get(4));
                if (productRowValues != null) {
                    List<String> matchingRowValues = new ArrayList<String>();
                    matchingRowValues.addAll(houseRowValues);
                    matchingRowValues.addAll(productRowValues);
                    System.out.println("match found:" + matchingRowValues);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }




}
