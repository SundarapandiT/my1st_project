<%@ page import="qrscanner.QRCodeScanner, datahandling.DatabaseHandler" %>
<html>
<body>
    <form action="scan" method="POST" enctype="multipart/form-data">
        Select QR Code to scan:
        <input type="file" name="qrfile" />
        <input type="submit" value="Scan">
    </form>

    <%
        String filePath = request.getParameter("qrfile");
        if (filePath != null) {
            String qrData = QRCodeScanner.scanQRCode(filePath);
            out.println("QR Code Data: " + qrData);

            String productDetails = DatabaseHandler.getProductDetails(qrData);
            out.println("<br/>Product Details: " + productDetails);
        }
    %>
</body>
</html>
