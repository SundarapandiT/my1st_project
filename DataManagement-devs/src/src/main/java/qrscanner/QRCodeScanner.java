package qrscanner;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import datahandling.DatabaseHandler;

public class QRCodeScanner {

    public static String scanQRCode(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("File does not exist: " + filePath);
                return null;
            }

            BufferedImage qrImage = ImageIO.read(file);
            if (qrImage == null) {
                System.err.println("Failed to read image from file: " + filePath);
                return null;
            }

            LuminanceSource source = new BufferedImageLuminanceSource(qrImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();  // Return the decoded text (product ID + details)
        } catch (NotFoundException e) {
            System.err.println("QR Code not found in the image: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the image file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String filePath = "QRs/Product_54321.png";  // Replace with the actual path to the QR code image

        // Scan the QR code
        String qrData = scanQRCode(filePath);

        if (qrData != null) {
            // Split the data to extract the product ID and product details
            String[] dataParts = qrData.split("-", 2);  // Splitting by the "-" delimiter

            if (dataParts.length == 2) {
                String productId = dataParts[0];       // Extract the product ID

                // Fetch the product details from the database using the product ID
                String productDetailsFromDB = DatabaseHandler.getProductDetails(productId);

                if (productDetailsFromDB != null) {
                    System.out.println("Product ID: " + productId);
                    System.out.println("Product Details from Database: " + productDetailsFromDB);
                } else {
                    System.out.println("Product not found in the database.");
                }
            } else {
                System.out.println("QR Code did not contain valid data.");
            }
        } else {
            System.out.println("Failed to read QR Code.");
        }
    }
}
