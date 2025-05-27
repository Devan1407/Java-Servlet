package com.magickitchen.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/updatePackage")
public class UpdatePackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String packageName = request.getParameter("package_name");
        String description = request.getParameter("description");

        try {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE food_packages SET package_name=?, description=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, packageName);
            ps.setString(2, description);
            ps.setInt(3, id);
            ps.executeUpdate();

            response.sendRedirect("chefDashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

