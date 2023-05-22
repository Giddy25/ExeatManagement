package Spring.LoginRegister.Master;

import Spring.LoginRegister.Entity.Group;
import Spring.LoginRegister.Entity.Teacher;


import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;

import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import org.springframework.stereotype.Service;

import javax.swing.text.StyleConstants;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
    public class GroupGeneratorService {
        private static final Map<Integer, String> ORDINAL_SUFFIXES = new HashMap<>() {{
            put(1, "st");
            put(2, "nd");
            put(3, "rd");
            put(21, "st");
            put(22, "nd");
            put(23, "rd");
            put(31, "st");
        }};

        private String formatDateWithOrdinalSuffix(LocalDate date) {
            int dayOfMonth = date.getDayOfMonth();
            String suffix = ORDINAL_SUFFIXES.getOrDefault(dayOfMonth, "th");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d'" + suffix + "' MMM yyyy");
            return date.format(formatter);
        }

        public String generatePdf(List<Group> weekGroups) throws IOException {
            // Create a new document
            String filename = "dutyroster.pdf";
            PdfDocument pdf = new PdfDocument(new PdfWriter(filename));

            // Create a new document
            Document document = new Document(pdf);

            // Load the font for the document
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);

            // Define the table properties
            int rowHeight = 20;
            int colWidth = 250;

            // Create the table
            Table table = new Table(new float[]{1,3,3});
            table.setWidthPercent(100);
            table.setBorder(new SolidBorder(1));

            // Add the table headers
            table.addHeaderCell(createCell("Week", font,1,true));
            table.addHeaderCell(createCell("Week Ending", font,60,true));
            table.addHeaderCell(createCell("Masters on Duty", font,60,true));

            // Iterate over the week groups and add a row to the table for each week
            for (int weekIndex = 0; weekIndex < weekGroups.size(); weekIndex++) {
                Group weekGroup = weekGroups.get(weekIndex);

                // Create the cells for the week row
                Cell weekCell = createCell(String.valueOf(weekIndex + 1), font,60,false);
                Cell endDateCell = createCell(formatDateWithOrdinalSuffix(weekGroup.getEndDate()), font,60,false);

                Cell teachersCell = createCell(getTeacherNames(weekGroup), font,300,false);

                // Add the cells to the table row
                table.addCell(weekCell);
                table.addCell(endDateCell);

                table.addCell(teachersCell);
            }

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();

            // Return the filename of the generated PDF
            return filename;
        }

        /**
         * Creates a cell with the specified text and font.
         */
        private Cell createCell(String text, PdfFont font, int colWidth,boolean isHeader) {

            Paragraph paragraph = new Paragraph(text).setFont(font);
            Cell cell=new Cell().add(paragraph);
            cell.setPadding(5);

            cell.setFontSize(11);
            cell.setMinHeight(20);
            cell.setBorder(new SolidBorder(1));
           if (isHeader) {
               cell.setBackgroundColor(Color.LIGHT_GRAY);
               cell.setTextAlignment(TextAlignment.CENTER);
               cell.setFontSize(12);
           }if(!isHeader &&colWidth==300){
                paragraph.setTextAlignment(TextAlignment.JUSTIFIED);
                cell.setTextAlignment(TextAlignment.LEFT);
                cell.setKeepTogether(true);
            }

            return cell;
        }



        /**
         * Returns a comma-separated string of teacher names for the specified week group.
         */
        private String getTeacherNames(Group weekGroup) {
            List<Teacher> teachers= weekGroup.getTeachers();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < teachers.size(); i++) {
                sb.append(teachers.get(i).getName());
                if (i < teachers.size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }



