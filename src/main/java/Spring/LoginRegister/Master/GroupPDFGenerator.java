//package Spring.LoginRegister.Master;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.List;
//
//import Spring.LoginRegister.Entity.Group;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.common.PDRectangle;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
//import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
//import org.springframework.stereotype.Service;
//
//@Service
//public class GroupPDFGenerator {
//
//    public static void generatePDF(OutputStream outputStream) {
//        try {
//            // Create a new PDF document
//            PDDocument document = new PDDocument();
//            PDPage page = new PDPage(PDRectangle.A4);
//            document.addPage(page);
//            PDAcroForm acroForm = new PDAcroForm(document);
//            document.getDocumentCatalog().setAcroForm(acroForm);
//            PDType1Font font = PDType1Font.HELVETICA;
//
//            // Add a text field for the group data
//            PDTextField tf = new PDTextField(acroForm);
//            tf.setPartialName("groupData");
//            acroForm.getFields().add(tf);
//
//            // Add a table of groups to the document
//            float margin = 50;
//            float yStart = page.getMediaBox().getHeight() - margin;
//            float tableWidth = page.getMediaBox().getWidth() - margin * 2;
//            float yPosition = yStart;
//            float rowHeight = 30;
//            float cellMargin = 10;
//
//            PDPageContentStream contentStream = new PDPageContentStream(document, page);
//            contentStream.beginText();
//            contentStream.setFont(font, 12);
//            contentStream.newLineAtOffset(margin, yPosition);
//            contentStream.showText("Week");
//            contentStream.newLineAtOffset(tableWidth / 3, 0);
//            contentStream.showText("Start Date");
//            contentStream.newLineAtOffset(tableWidth / 3, 0);
//            contentStream.showText("End Date");
//            contentStream.endText();
//            yPosition -= rowHeight;
//            List<Group> weekGroups = null;
//            for (Group group : weekGroups) {
//                contentStream.beginText();
//                contentStream.setFont(font, 12);
//                contentStream.newLineAtOffset(margin, yPosition);
//                contentStream.showText(Integer.toString(group.getWeekNumber()));
//                contentStream.newLineAtOffset(tableWidth / 3, 0);
//                contentStream.showText(group.getStartDate().toString());
//                contentStream.newLineAtOffset(tableWidth / 3, 0);
//                contentStream.showText(group.getEndDate().toString());
//                contentStream.endText();
//                yPosition -= rowHeight;
//            }
//
//            // Set the value of the group data field
//            tf.setValue("Group data");
//
//            contentStream.close();
//            document.save(outputStream);
//            document.close();
//            System.out.println("PDF generated successfully.");
//        } catch (IOException e) {
//            System.err.println("Error generating PDF: " + e.getMessage());
//        }
//    }
//}
