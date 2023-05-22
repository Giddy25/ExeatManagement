package Spring.LoginRegister.Admin;


import Spring.LoginRegister.Entity.Student;
import Spring.LoginRegister.House.HouseRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

public class ExcelHelper {
@Autowired
private  HouseRepository houseRepository;

    private static Cell currentCell;


    //check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }

    }


    //convert excel to list of products

    public String convertExcelToListOfStudent(InputStream is) {
        List<Student> students = new ArrayList<>();


        try {


            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet("studentsList");


            Iterator<Row> rows = sheet.iterator();
            students = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Student student = new Student();


                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            student.setStudentNumber(currentCell.getStringCellValue());
                            System.out.println("studentNumber:" + currentCell.getStringCellValue());
                            break;

                        case 1:
                            student.setStudentName(currentCell.getStringCellValue());
                            System.out.println("Name:" + currentCell.getStringCellValue());

                            break;

                        case 2:
                            student.setStudentClass(currentCell.getStringCellValue());
                            System.out.println("class:" + currentCell.getStringCellValue());

                            break;

                        case 3:
                            student.setParentContact(currentCell.getStringCellValue());
                            System.out.println("contact:" + currentCell.getStringCellValue());
                            break;
                        case 4:
                            student.setSex(currentCell.getStringCellValue());

                            System.out.println("sex:" + currentCell.getStringCellValue());
                        break;

                        case 5:
int houseID = (int) currentCell.getNumericCellValue();


                            System.out.println("house:" + houseID);
//
//                               Cell HouseIDCell = currentRow.getCell(5);
//                               int House = (int) HouseIDCell.getNumericCellValue();
//                            System.out.println("house:" +House);
//
//House house = houses.get(House);
//student.setHouse(house);
//                            System.out.println("house:" +house);

                        default:
                            break;
                    }

                    cellIdx++;
                }

                students.add(student);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
