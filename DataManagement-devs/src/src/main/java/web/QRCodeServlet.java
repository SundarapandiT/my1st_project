//package web;
//
//import qrscanner.QRCodeScanner;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//
//@WebServlet("/scan")
//public class QRCodeServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) uploadDir.mkdir();
//
//        // Get the file name from the form
//        String qrFilePath = Paths.get(uploadPath, "scanned_qr.png").toString();
//        // Handle file upload manually here, for now assume it’s saved to 'qrFilePath'
//
//        // Scan the QR code using QRCodeScanner
//        String qrData = QRCodeScanner.scanQRCode(qrFilePath);
//
//        response.setContentType("text/html");
//        response.getWriter().println("<html><body>");
//
//        if (qrData != null) {
//            response.getWriter().println("QR Code Data: " + qrData);
//        } else {
//            response.getWriter().println("Failed to read QR Code.");
//        }
//
//        response.getWriter().println("</body></html>");
//    }
//}
