package com.magickitchen.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/addPackage")
public class AddPackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String packageName = request.getParameter("package_name");
        String description = request.getParameter("description");

        // Get the user ID from the session (assuming the admin is logged in)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            out.println("<h3>Error: Unauthorized access. Please log in.</h3>");
            return;
        }

        int createdBy = (int) session.getAttribute("user_id"); // Fetch user_id from session

        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO food_packages (package_name, description, created_by) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, packageName);
            ps.setString(2, description);
            ps.setInt(3, createdBy);
            
            int result = ps.executeUpdate();
            con.close();
            
            if (result > 0) {
                response.sendRedirect("adminDashboard");
            } else {
                out.println("<h3>Failed to add package.</h3>");
            }
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}

