package datahandling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/warehouse";
    private static final String USER = "root";
    private static final String PASSWORD = "Sundar@7";  // Replace with your password

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getProductDetails(String productId) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Product: " + rs.getString("name") + ", Quantity: " + rs.getInt("quantity");
            } else {
                return "Product not found!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getAllProductIds() throws SQLException {
        String query = "SELECT product_id FROM products";
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

    public static void main(String[] args) {
        String productId = "12345";  // Example product ID
        System.out.println(getProductDetails(productId));
    }
}
