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

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();
            out.println("<html><head><title>Admin Dashboard</title>");
            out.println("<link rel='stylesheet' href='css/style.css'></head><body>");
            out.println("<h2>Admin Dashboard</h2>");

            // Add User Button
            out.println("<button onclick=\"window.location.href='registerUserForm.html'\">Add User</button>");

            // Display Customers
            out.println("<h3>Manage Customers</h3>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Actions</th></tr>");

            String query = "SELECT id, name, email, phone FROM users WHERE role='customer'";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + phone + "</td>");
                out.println("<td><a href='editCustomer?id=" + id + "'>Edit</a> | "
                        + "<a href='deleteCustomer?id=" + id + "' onclick='return confirm(\"Are you sure?\")'>Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");

            // Display Chefs
            out.println("<h3>Manage Chefs</h3>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Actions</th></tr>");

            query = "SELECT id, name, email, phone FROM users WHERE role='chef'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + phone + "</td>");
                out.println("<td><a href='editChef?id=" + id + "'>Edit</a> | "
                        + "<a href='deleteChef?id=" + id + "' onclick='return confirm(\"Are you sure?\")'>Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");

            // Add Package Button
            out.println("<button onclick=\"location.href='addPackageForm.html'\" style='margin-bottom: 20px;'>Add New Package</button>");

            // Display Food Packages
            out.println("<h3>Manage Food Packages</h3>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Package Name</th><th>Description</th><th>Actions</th></tr>");

            query = "SELECT id, package_name, description FROM food_packages";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String packageName = rs.getString("package_name");
                String description = rs.getString("description");
                
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + packageName + "</td>");
                out.println("<td>" + description + "</td>");
                out.println("<td><a href='editPackage?id=" + id + "'>Edit</a> | "
                        + "<a href='deletePackage?id=" + id + "' onclick='return confirm(\"Are you sure?\")'>Delete</a></td>");
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
