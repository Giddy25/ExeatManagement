package Spring.LoginRegister.Controller;


import Spring.LoginRegister.Entity.House;
import Spring.LoginRegister.Entity.Student;
import Spring.LoginRegister.House.HouseRepository;
import Spring.LoginRegister.Student.StudentRepository;
import Spring.LoginRegister.excel.ExcelService;
import Spring.LoginRegister.House.HouseService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@CrossOrigin("*")
public class ExcelController {
    private static Cell currentCell;


    @Autowired
    private ExcelService excelService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
//        if (Helper.checkExcelFormat(file)) {
        //true
//            houseService.findHouse(house.getHouseID());


        //check that file is of excel type or not


        String contentType = file.getContentType();




        //convert excel to list of products


        List<Student> students = new ArrayList<>();
        List<String> skippedRows = new ArrayList<>();

        try {


            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

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
                boolean skipRow = false;
                while (cellsInRow.hasNext()) {
                    currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            String studentNumber = currentCell.getStringCellValue();
//                            check if Student Number already exist
                            if (studentRepository.findStudentByStudentNumber(studentNumber) != null) {
                                System.out.println("Student with student number: " + studentNumber + " already exists. Skipping to the next");
//                                skip this row
                                skipRow = true;
//                      Add skipped Row information to list
                                skippedRows.add("Skipped row number: " + currentRow.getRowNum() + " with studentNumber: " +studentNumber);
                                break;
                            }
                            student.setStudentNumber(studentNumber);
                            System.out.println("studentNumber:" + currentCell.getStringCellValue());
                            break;

                        case 1:
                            student.setStudentName(currentCell.getStringCellValue());
                            System.out.println("Name:" + currentCell.getStringCellValue());

                            break;

                        case 2:
                            student.setStudentClass(currentCell.getStringCellValue());
                            System.out.println("class" + currentCell.getStringCellValue());

                            break;

                        case 3:
                            student.setParentContact((currentCell.getStringCellValue()));
                            System.out.println("contact:" + currentCell.getStringCellValue());
                            break;
                        case 4:
                            student.setSex(currentCell.getStringCellValue());

                            System.out.println("sex:" + currentCell.getStringCellValue());
                            break;

                        case 5:
                            String houseName = currentCell.getStringCellValue();
                            House house = houseRepository.findByhouseName(houseName);

                            if (house == null && skipRow==true){
//Add Skipped row information to list
                                skippedRows.add("Skipped row number: " + currentRow.getRowNum() + " with houseName: " +houseName);
                            }
                            else if (house == null && !skipRow) {
                                house = new House();
                                house.setHouseName(houseName);
                                houseRepository.save(house);
                            }
                            student.setHouse(house);
                            System.out.println("house:" + houseName);

                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }
                if (!skipRow) {
                    students.add(student);
                }
            }
                studentRepository.saveAll(students);

        } catch (Exception e) {
            e.printStackTrace();
        }


    System.out.println(skippedRows);

        model.addAttribute("skippedRows", skippedRows);
        return "redirect:/master/MasterDashBoard?success";
    }

}


//            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));



//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");



//    @GetMapping("/Student")
//    public List<Student> getAllStudent() {
//
//        return this.excelService.getAllStudents();
//    }


