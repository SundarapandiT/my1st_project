import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class QRCodeGenerator {

    public static void generateProductQRCodes(Connection connection, String qrCodeDir) throws WriterException, IOException, SQLException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        // Use a map with an array to store product name and quantity
        HashMap<String, String[]> products = new HashMap<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, name, stock_count FROM products")) {

            // Fetch data from database
            while (resultSet.next()) {
                String productId = resultSet.getString("id");
                String productName = resultSet.getString("name");
                String productQuantity = resultSet.getString("stock_count");

                // Store name and quantity as array in HashMap
                products.put(productId, new String[]{productName, productQuantity});
            }

            // Generate QR codes for each product
            for (String productId : products.keySet()) {
                String[] productDetails = products.get(productId);
                String productName = productDetails[0];
                String productQuantity = productDetails[1];
                
                // Combine product ID, name, and quantity for the QR code text
                String qrCodeText = productId + "," + productName + "," + productQuantity;

                // Create QR code directory if it doesn't exist
                File qrCodeFolder = new File(qrCodeDir);
                if (!qrCodeFolder.exists()) {
                    qrCodeFolder.mkdirs();
                }

                // Generate file path for the QR code image
                String filePath = qrCodeDir + File.separator + productId + ".png";
                Path path = FileSystems.getDefault().getPath(filePath);

                // Generate and save the QR code
                BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 200, 200);
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

                System.out.println("Generated QR code for product: " + productId);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Get connection from your database.java class
        Connection connection = Database.getConnection(); // Ensure getConnection() exists in database.java

        String qrCodeDir = "QRCodes"; // Directory for storing QR codes

        generateProductQRCodes(connection, qrCodeDir);

        // Close the connection if necessary (or use connection pooling)
        connection.close();
    }
}
