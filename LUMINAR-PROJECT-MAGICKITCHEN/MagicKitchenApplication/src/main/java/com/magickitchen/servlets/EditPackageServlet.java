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


@WebServlet("/editPackage")
public class EditPackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM food_packages WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>Edit Package</title></head><body>");
            out.println("<h2>Edit Food Package</h2>");

            if (rs.next()) {
                out.println("<form action='updatePackage' method='post'>");
                out.println("<input type='hidden' name='id' value='" + rs.getInt("id") + "'>");
                out.println("Package Name: <input type='text' name='package_name' value='" + rs.getString("package_name") + "' required><br>");
                out.println("Description: <textarea name='description' required>" + rs.getString("description") + "</textarea><br>");
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
}
