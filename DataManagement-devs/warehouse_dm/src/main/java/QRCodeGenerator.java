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

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, name FROM products")) {

            HashMap<String, String> products = new HashMap<>();
            while (resultSet.next()) {
                String productId = resultSet.getString("id");
                String productName = resultSet.getString("name");
                products.put(productId, productName);
            }

            for (String productId : products.keySet()) {
                String productName = products.get(productId);
                String qrCodeText = productId + "," + productName; // Combine product ID and name

                // Create QR code directory if it doesn't exist
                File qrCodeFolder = new File(qrCodeDir);
                if (!qrCodeFolder.exists()) {
                    qrCodeFolder.mkdirs();
                }

                String filePath = qrCodeDir + File.separator + productId + ".png"; // Build file path
                Path path = FileSystems.getDefault().getPath(filePath);

                BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 200, 200);
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

                System.out.println("Generated QR code for product: " + productId);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Get connection from your database.java class
        Connection connection = Database.getConnection(); // getConnection() exists in database.java

        String qrCodeDir = "QRCodes"; // Update with your desired directory

        generateProductQRCodes(connection, qrCodeDir);

        // Close the connection (if necessary) - consider implementing a connection pool in database.java for better resource management
        connection.close();
    }
}