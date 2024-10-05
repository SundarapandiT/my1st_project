<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Warehouse Stock Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            display: flex;
            flex-direction: column; /* Stack elements vertically */
            align-items: center; /* Center elements horizontally */
            width: 100%; /* Make sure it takes full width */
            max-width: 400px; /* Limit the maximum width */
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: white; /* Background for the container */
        }

        h2 {
            color: #333;
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }

        label {
            font-weight: bold;
            color: #555;
            margin-bottom: 10px;
            display: block;
        }

        input[type="text"], input[type="file"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #5cb85c;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #4cae4c;
        }

        p {
            font-size: 18px;
            color: #333;
            margin-top: 20px;
            text-align: left; /* Align text to the left */
            line-height: 1.5; /* Add space between lines */
            width: 100%; /* Ensure paragraphs take full width */
        }

        .message {
            color: #d9534f;
            font-weight: bold;
        }

        .product-info {
            color: #5bc0de;
            font-weight: bold;
            margin-bottom: 10px; /* Space between product info lines */
            display: block; /* Ensure each detail is displayed as a block */
        }
    </style>
</head>
<body>

    <div class="container">
        <form action="${pageContext.request.contextPath}/ProductServlet" method="post" enctype="multipart/form-data">
            <h2>Warehouse Stock Management</h2>

            <!-- File upload for QR code -->
            <label for="qrCodeFile">Upload QR Code:</label>
            <input type="file" id="qrCodeFile" name="qrCodeFile" accept="image/*" required>

            <input type="submit" value="Get Product Details">
        </form>

        <%
            // Display product information if available
            String productId = (String) request.getAttribute("productId");
            String productName = (String) request.getAttribute("productName");
            Integer currentStock = (Integer) request.getAttribute("stock_count");
            String message = (String) request.getAttribute("message");

            if (productId != null && productName != null && currentStock != null) {
    %>
            <h2>Product Details</h2>
            <p class='product-info'>Product ID: <%= productId %></p>
            <p class='product-info'>Product Name: <%= productName %></p>
            <p class='product-info'>Current Stock: <%= currentStock %></p>

            <!-- Stock Update Form -->
            <form action="${pageContext.request.contextPath}/ProductServlet" method="post">
                <label for="stockUpdate">New Stock Count:</label>
                <input type="text" id="stockUpdate" name="stockUpdate" placeholder="Enter new stock count" required>
                <input type="hidden" name="productId" value="<%= productId %>"> <!-- Store product ID for updating -->
                <input type="submit" value="Update Stock">
            </form>
    <%
            }

            if (message != null) {
                out.println("<p class='message'>" + message + "</p>");
            }
    %>
    </div> <!-- Close the container div -->

</body>
</html>
