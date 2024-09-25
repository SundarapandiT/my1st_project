package optimization;

import datahandling.DatabaseHandler;

import java.sql.Connection;
import java.sql.Statement;

public class DataOptimizer {

    public static void removeDuplicateData() {
        String query = "DELETE t1 FROM products t1 INNER JOIN products t2 " +
                "WHERE t1.id < t2.id AND t1.product_id = t2.product_id";

        try (Connection conn = DatabaseHandler.connect();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            System.out.println("Removed " + rowsAffected + " duplicate entries.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        removeDuplicateData();
    }
}
