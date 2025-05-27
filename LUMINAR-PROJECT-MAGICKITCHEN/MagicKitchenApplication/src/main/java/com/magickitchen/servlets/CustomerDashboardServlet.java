package com.magickitchen.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/customerDashboard")
public class CustomerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT id, package_name, description FROM food_packages";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>Customer Dashboard</title>");
            out.println("<link rel='stylesheet' href='css/style.css'></head><body>");
            out.println("<h2>Available Food Packages</h2>");

            // Remove Action column
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Package Name</th><th>Description</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");
                String packageName = rs.getString("package_name");
                String description = rs.getString("description");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + packageName + "</td>");
                out.println("<td>" + description + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
