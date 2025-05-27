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

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int customerId = (int) session.getAttribute("userId");
        int packageId = Integer.parseInt(request.getParameter("packageId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO orders (customer_id, package_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            stmt.setInt(2, packageId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
            response.sendRedirect("customerDashboard.html?success=Order placed");
        } catch (Exception e) {
            throw new ServletException("Error placing order", e);
        }
    }
}
