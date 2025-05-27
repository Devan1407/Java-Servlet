package com.luminar;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateitem")
public class UpdateItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/luminar_servlet";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			int itemId = Integer.parseInt(request.getParameter("itemId"));

			String itemName = request.getParameter("itemName").toUpperCase().trim();

			int itemqty = Integer.parseInt(request.getParameter("itemqty"));

			/*
			 * pst = con.prepareStatement("update item set item_name='"+itemName+"', " +
			 * "item_quantity="+itemqty+" where item_id="+itemId+"");
			 */

			/*
			 * System.out.println("update item set item_name='"+itemName+"', " +
			 * "item_quantity="+itemqty+" where item_id="+itemId+"");
			 * 
			 */

			pst = con.prepareStatement("update items set item_name=?," + "item_qty=? where item_id=?");
			pst.setString(1, itemName);
			pst.setInt(2, itemqty);
			pst.setInt(3, itemId);

			pst.executeUpdate();
			con.close();
			dis = request.getRequestDispatcher("admin");
			dis.include(request, response);

			// or
			// response.sendRedirect("index.html");
			
			

		} catch (IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}
