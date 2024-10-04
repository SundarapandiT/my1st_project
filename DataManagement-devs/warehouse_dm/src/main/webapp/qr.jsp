<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>QR Code Warehouse</title>
</head>
<body>
<h1>QR Code Data Retrieval</h1>
<form action="ProductServlet" method="post">
    <label for="qrData">QR Data:</label>
    <input type="text" name="qrData" id="qrData" required>
    <button type="submit">Retrieve Data</button>
</form>
</body>
</html>
