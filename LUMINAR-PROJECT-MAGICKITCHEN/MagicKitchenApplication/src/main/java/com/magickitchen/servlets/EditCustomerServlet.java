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

@WebServlet("/editCustomer")
public class EditCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int customerId = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT id, name, email, phone FROM users WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>Edit Customer</title></head><body>");
            out.println("<h2>Edit Customer</h2>");

            if (rs.next()) {
                out.println("<form action='editCustomer' method='post'>");
                out.println("<input type='hidden' name='id' value='" + rs.getInt("id") + "'>");
                out.println("Name: <input type='text' name='name' value='" + rs.getString("name") + "'><br>");
                out.println("Email: <input type='email' name='email' value='" + rs.getString("email") + "'><br>");
                out.println("Phone: <input type='text' name='phone' value='" + rs.getString("phone") + "'><br>");
                out.println("<input type='submit' value='Update'>");
                out.println("</form>");
            }

            out.println("</body></html>");
            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE users SET name=?, email=?, phone=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, id);
            ps.executeUpdate();

            response.sendRedirect("adminDashboard");
            con.close();
        } catch (Exception e) {
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
