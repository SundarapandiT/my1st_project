<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Generate QR Code for Product</title>
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
            width: 100%;
            max-width: 400px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: white;
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

        input[type="text"], input[type="submit"] {
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
        }

        input[type="submit"]:hover {
            background-color: #4cae4c;
        }

        p {
            font-size: 18px;
            color: #333;
            margin-top: 20px;
            text-align: left;
            line-height: 1.5;
            width: 100%;
        }

        .message {
            color: #d9534f;
            font-weight: bold;
        }

    </style>
</head>
<body>

    <div class="container">
        <h2>Generate QR Code for Product</h2>
        <form action="${pageContext.request.contextPath}/QRGenerateServlet" method="post">
            <label for="productId">Product ID:</label>
            <input type="text" id="productId" name="productId" placeholder="Enter Product ID" required>

            <label for="productName">Product Name:</label>
            <input type="text" id="productName" name="productName" placeholder="Enter Product Name" required>

            <label for="stockCount">Stock Count:</label>
            <input type="text" id="stockCount" name="stockCount" placeholder="Enter Stock Count" required>

            <input type="submit" value="Generate QR Code">
        </form>

        <%
            String message = (String) request.getAttribute("message");
            String qrCodePath = (String) request.getAttribute("qrCodePath");

            if (message != null) {
                out.println("<p class='message'>" + message + "</p>");
            }

            if (qrCodePath != null) {
                out.println("<p class='message'>QR Code saved at: " + qrCodePath + "</p>");
            }
        %>
    </div>

</body>
</html>
