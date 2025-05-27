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

@WebServlet("/deleteitem")
public class DeleteItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/luminar_servlet";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	RequestDispatcher dis = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			int itemId = Integer.parseInt(request.getParameter("did"));		
					
			pst = con.prepareStatement("delete from items  where item_id="+itemId+"");
			
		
			pst.executeUpdate();
			con.close();
			
			dis = request.getRequestDispatcher("admin");
			dis.include(request, response);
			
			
		} catch (IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}
