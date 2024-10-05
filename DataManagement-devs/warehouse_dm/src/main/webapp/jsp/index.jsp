<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Warehouse Product Information</title>
</head>
<body>
    <h2>Product Details</h2>

    <!-- Correct the form action to point to /ProductServlet -->
    <form action="${pageContext.request.contextPath}/ProductServlet" method="post">
        <label for="productId">Product ID:</label>
        <input type="text" id="productId" name="productId" required>
        <input type="submit" value="Get Product Info">
    </form>

    <%
        String productName = (String) request.getAttribute("productName");
        Integer quantity = (Integer) request.getAttribute("stock_count");
        String message = (String) request.getAttribute("message");

        if (productName != null && quantity != null) {
            out.println("<p>Product Name: " + productName + "</p>");
            out.println("<p>Quantity: " + quantity + "</p>");
        } else if (message != null) {
            out.println("<p>" + message + "</p>");
        }
    %>
</body>
</html>
