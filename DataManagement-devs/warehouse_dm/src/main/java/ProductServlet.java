import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String qrData = request.getParameter("qrData");

        if (qrData != null) {
            try (Connection connection = Database.getConnection()) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM products WHERE id = ?");
                stmt.setString(1, qrData);
                
                // Log the prepared statement for debugging
                System.out.println("Executing query: SELECT * FROM products WHERE id = " + qrData);
                
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String productName = rs.getString("name");
                    int stockCount = rs.getInt("stock_count");
                    response.getWriter().write("Product: " + productName + ", Stock: " + stockCount);
                } else {
                    response.getWriter().write("Product not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("Database error occurred: " + e.getMessage());
            }
        } else {
            response.getWriter().write("QR data is missing.");
        }

    }
}
