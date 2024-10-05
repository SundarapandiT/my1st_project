import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

@WebServlet("/ProductServlet")
@MultipartConfig // Enables file upload handling
public class ProductServlet extends HttpServlet {

    // Handle POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    // Handle GET requests by routing them to the processRequest method
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);  // Calls the same logic as POST
    }

    // Common method for both GET and POST requests
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stockUpdate = request.getParameter("stockUpdate"); // Check if this parameter is present
        String productId = request.getParameter("productId"); // Check if this parameter is present

        if (productId != null) {
            // Update the product stock
            if (stockUpdate != null && !stockUpdate.isEmpty()) {
                updateProductStock(productId, Integer.parseInt(stockUpdate), request, response);
                return; // Exit after updating stock
            }

            // Fetch product details based on the product ID from the QR code
            fetchProductDetails(productId, request, response);
            return; // Exit after fetching product details
        }

        Part filePart = request.getPart("qrCodeFile"); // Gets the file part (QR code image)
        if (filePart != null && filePart.getSize() > 0) {
            // Save the file temporarily
            String fileName = filePart.getSubmittedFileName();
            File file = new File(getServletContext().getRealPath("/") + fileName);
            filePart.write(file.getAbsolutePath());

            // Read the QR code from the file and extract the product ID
            String productIdFromQR = readQRCode(file.getAbsolutePath()).trim();  // Trim spaces for safety
            System.out.println("Product ID from QR code: " + productIdFromQR);  // Debugging log
            
            if (productIdFromQR != null) {
                // Fetch product details using the decoded product ID
                fetchProductDetails(productIdFromQR, request, response);
            } else {
                request.setAttribute("message", "Failed to read QR code.");
                request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
            }

            // Clean up the temporary file
            file.delete();
        } else {
            request.setAttribute("message", "File upload failed or file was empty.");
            request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
        }
    }

    // Method to read QR code from the image file and extract only the product ID
    private String readQRCode(String filePath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            
            // Assuming the QR code result is in the format "Product123,Laptop,50"
            String qrCodeData = result.getText();
            System.out.println("QR Code Result: " + qrCodeData);  // Log the decoded QR code data

            // Split the string by commas and extract the product ID (first part)
            String[] dataParts = qrCodeData.split(",");
            String productId = dataParts[0].trim();  // Extract the product ID (e.g., "Product123")

            System.out.println("Extracted Product ID: " + productId);  // Log the extracted product ID
            return productId;
            
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fetch product details based on product ID
    private void fetchProductDetails(String productId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, productId);  // Use VARCHAR productId

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                request.setAttribute("productId", productId);
                request.setAttribute("productName", rs.getString("name"));
                request.setAttribute("stock_count", rs.getInt("stock_count"));
            } else {
                request.setAttribute("message", "Product not found.");
            }
            request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update product stock based on product ID
    private void updateProductStock(String productId, int newStockCount, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = Database.getConnection()) {
            String query = "UPDATE products SET stock_count = ? WHERE id = ?"; // Adjusting for VARCHAR ID
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, newStockCount);
            ps.setString(2, productId);  // Using VARCHAR productId
            
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                request.setAttribute("message", "Stock updated successfully.");
                // Re-fetch product details after update to display updated info
                fetchProductDetails(productId, request, response);
            } else {
                request.setAttribute("message", "Product not found.");
                request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
