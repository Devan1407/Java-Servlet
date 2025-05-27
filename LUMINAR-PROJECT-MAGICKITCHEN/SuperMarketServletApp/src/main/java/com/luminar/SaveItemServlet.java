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


@WebServlet("/saveitem")
public class SaveItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/luminar_servlet";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			
			String itemName = request.getParameter("itemName").toUpperCase().trim();

			int itemqty = Integer.parseInt(request.getParameter("itemqty"));
			

			pst = con.prepareStatement("insert into items(item_name,item_qty)values(?,?)");
			pst.setString(1, itemName);
			pst.setInt(2, itemqty);
			

			pst.executeUpdate();
			
			con.close();
			
			dis = request.getRequestDispatcher("admin");
			dis.include(request, response);

		
		} catch (IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}
