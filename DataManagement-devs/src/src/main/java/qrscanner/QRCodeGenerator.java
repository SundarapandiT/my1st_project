package qrscanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import datahandling.DatabaseHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QRCodeGenerator {

    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        // Ensure the directory exists
        File file = new File(filePath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void generateQRCodesForAllProducts() {
        // Retrieve all product IDs from the database
        try (ResultSet rs = DatabaseHandler.getAllProductIds()) {
            while (rs.next()) {
            	String dw="Devs Datawarehouse ";
                String productId = rs.getString("product_id");
                String productDetails = productId+"-"+dw+DatabaseHandler.getProductDetails(productId);
               System.out.println(productDetails);
                String filePath = "QRs/Product_" + productId + ".png";
                generateQRCodeImage(productDetails, 350, 350, filePath);
                System.out.println("QR Code Generated: " + filePath);
            }
        } catch (SQLException | WriterException | IOException e) {
            System.out.println("Could not generate QR Codes: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        generateQRCodesForAllProducts();
    }
}
