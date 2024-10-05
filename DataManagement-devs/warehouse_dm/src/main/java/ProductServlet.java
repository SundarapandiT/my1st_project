import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {

    // Handle POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    // Handle GET requests by routing them to the processRequest method
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);  // Calls the same logic as POST
    }

    // Common method for both GET aand POST requests
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");

        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM products WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
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
}
