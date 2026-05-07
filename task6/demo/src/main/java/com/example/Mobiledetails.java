package com.example;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; // Explicitly import the SQL version

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Mobiledetails")
public class Mobiledetails extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mobile";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String model = normalize(req.getParameter("ModelId"));
        String priceStr = normalize(req.getParameter("Price"));
        String company = normalize(req.getParameter("Company"));
        String color = normalize(req.getParameter("Color"));

        try (PrintWriter out = resp.getWriter()) {
            out.println("<html><body>");

            if (model.isEmpty() || priceStr.isEmpty() || company.isEmpty() || color.isEmpty()) {
                out.println("<h3 style='color:red;'>All fields are required.</h3>");
                out.println("<a href='index.html'>Go Back</a>");
                out.println("</body></html>");
                return;
            }

            int price;
            try {
                price = Integer.parseInt(priceStr);
                if (price < 0) {
                    throw new NumberFormatException("Price cannot be negative");
                }
            } catch (NumberFormatException e) {
                out.println("<h3 style='color:red;'>Price must be a valid non-negative number.</h3>");
                out.println("<a href='index.html'>Go Back</a>");
                out.println("</body></html>");
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String insertQuery = "INSERT INTO mobiledetails (MobileId, Price, Company, Color) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                    ps.setString(1, model);
                    ps.setInt(2, price);
                    ps.setString(3, company);
                    ps.setString(4, color);

                    int rowsInserted = ps.executeUpdate();
                    if (rowsInserted > 0) {
                        out.println("<h3 style='color:green;'>Record inserted successfully.</h3>");
                    }
                }

                try (Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM mobiledetails")) {

                    out.println("<table border='1' style='width:50%; border-collapse: collapse;'>");
                    out.println("<tr style='background-color:#ffffb3;'><th>Model Id</th><th>Price</th><th>Company</th><th>Color</th></tr>");

                    while (rs.next()) {
                        out.println("<tr>");
                        out.println("<td>" + escapeHtml(rs.getString("MobileId")) + "</td>");
                        out.println("<td>" + rs.getInt("Price") + "</td>");
                        out.println("<td>" + escapeHtml(rs.getString("Company")) + "</td>");
                        out.println("<td>" + escapeHtml(rs.getString("Color")) + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }

                out.println("<br><a href='index.html'>Add Another Mobile</a>");
            }
            out.println("</body></html>");
        } catch (Exception e) {
            throw new ServletException("Unable to save or load mobile details. Check the database connection and table setup.", e);
        }
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
