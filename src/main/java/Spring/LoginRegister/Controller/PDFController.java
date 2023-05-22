//package Spring.LoginRegister.Controller;
//
//import Spring.LoginRegister.Entity.Group;
//import Spring.LoginRegister.Master.GroupPDFGenerator;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class PDFController {
//
//    @GetMapping("/generate-groups-pdf")
//    public String generateGroupsPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        // Set response headers
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"groups.pdf\"");
//
//        // Generate PDF and write it to response output stream
//        ServletOutputStream outputStream = response.getOutputStream();
//        groupPDFGenerator.generatePDF(outputStream);
//        outputStream.flush();
//        outputStream.close();
//        return "dutyrosterPdf";
//    }
//}
