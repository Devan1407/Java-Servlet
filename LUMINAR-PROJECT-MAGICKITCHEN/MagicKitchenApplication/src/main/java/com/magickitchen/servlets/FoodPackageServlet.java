package com.magickitchen.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/FoodPackageServlet")
public class FoodPackageServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId"); // Chef/Admin ID
        
        try (Connection conn = DBConnection.getConnection()) {
            if ("add".equals(action)) {
                String packageName = request.getParameter("packageName");
                String description = request.getParameter("description");
                String sql = "INSERT INTO food_packages (package_name, description, created_by) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, packageName);
                stmt.setString(2, description);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
                response.sendRedirect("chefDashboard.html?success=Package added");
            } 
            else if ("delete".equals(action)) {
                int packageId = Integer.parseInt(request.getParameter("packageId"));
                String sql = "DELETE FROM food_packages WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, packageId);
                stmt.executeUpdate();
                response.sendRedirect("chefDashboard.html?success=Package deleted");
            }
        } catch (Exception e) {
            throw new ServletException("Error managing food packages", e);
        }
    }
}
