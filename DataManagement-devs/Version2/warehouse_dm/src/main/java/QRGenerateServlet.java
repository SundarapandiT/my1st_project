import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/QRGenerateServlet")
public class QRGenerateServlet extends HttpServlet {

    // Handle POST requests for generating the QR code
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    // Handle GET requests (redirect to POST method)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    // Common method to handle both GET and POST requests
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get product details from the form
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String stockCount = request.getParameter("stockCount");

        if (productId != null && productName != null && stockCount != null) {
            try {
                // Insert or update the product in the database
                storeProductInDatabase(productId, productName, Integer.parseInt(stockCount));

                // Generate QR code after storing the product
                generateQRCodeForProduct(productId, productName, stockCount, request, response);
            } catch (SQLException | WriterException e) {
                e.printStackTrace();
                request.setAttribute("message", "Failed to generate QR code for product: " + productId);
                request.getRequestDispatcher("/jsp/qrgen.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "Please provide all product details for QR code generation.");
            request.getRequestDispatcher("/jsp/qrgen.jsp").forward(request, response);
        }
    }

    // Generate QR code for product and save to the specified directory
    private void generateQRCodeForProduct(String productId, String productName, String stockCount, HttpServletRequest request, HttpServletResponse response) throws WriterException, IOException, ServletException {
        try {
            // Combine product details into QR code text
            String qrCodeText = productId + "," + productName + "," + stockCount;
            String qrCodeDir = getServletContext().getRealPath("QRCodes"); // Directory to store QR codes

            // Create QR code directory if it doesn't exist
            java.io.File qrCodeFolder = new java.io.File(qrCodeDir); // Fully qualify the File class
            if (!qrCodeFolder.exists()) {
                boolean created = qrCodeFolder.mkdirs(); // Create the directory if it doesn't exist
                if (!created) {
                    throw new IOException("Failed to create directory: " + qrCodeDir);
                }
            }

            // Generate QR code image
            Path path = FileSystems.getDefault().getPath(qrCodeDir, productId + ".png");
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            // Set success message and path to the generated QR code
            request.setAttribute("message", "QR code generated successfully for product: " + productId);
            request.setAttribute("qrCodePath", path.toString());
            request.getRequestDispatcher("/jsp/qrgen.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace to the console for debugging
            request.setAttribute("message", "Error generating QR code for product.");
            request.getRequestDispatcher("/jsp/qrgen.jsp").forward(request, response);
        }
    }

    // Store the product in the database (insert if new, update if existing)
    private void storeProductInDatabase(String productId, String productName, int stockCount) throws SQLException {
        try (Connection conn = Database.getConnection()) {
            // Check if the product already exists
            String selectQuery = "SELECT * FROM products WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(selectQuery)) {
                ps.setString(1, productId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    // If the product exists, update it
                    String updateQuery = "UPDATE products SET name = ?, stock_count = ? WHERE id = ?";
                    try (PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {
                        updatePs.setString(1, productName);
                        updatePs.setInt(2, stockCount);
                        updatePs.setString(3, productId);
                        updatePs.executeUpdate();
                    }
                } else {
                    // If the product doesn't exist, insert it
                    String insertQuery = "INSERT INTO products (id, name, stock_count) VALUES (?, ?, ?)";
                    try (PreparedStatement insertPs = conn.prepareStatement(insertQuery)) {
                        insertPs.setString(1, productId);
                        insertPs.setString(2, productName);
                        insertPs.setInt(3, stockCount);
                        insertPs.executeUpdate();
                    }
                }
            }
        }
    }
}
