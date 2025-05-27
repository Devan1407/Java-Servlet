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
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/luminar_servlet";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	RequestDispatcher dis = null;
	
	String userName="";
	String userRole="";

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException,IOException{
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			
			String uname=request.getParameter("uname");
			String upass=request.getParameter("upass");
			
			pst = con.prepareStatement("select login_user_name, login_role from login where "
					+ "login_user_name=? "
					+ "and "
					+ "login_password=?"); 
			
			pst.setString(1, uname);
			pst.setString(2, upass);		
			
			rs = pst.executeQuery();
			
			while(rs.next()){
				userName= rs.getString(1);
				userRole= rs.getString(2);
			}
			
			HttpSession session=request.getSession();			
			session.setAttribute("user",userName);
			
			if(userRole.equals("Admin")){
				dis = request.getRequestDispatcher("admin");
				dis.include(request, response);
			}else if(userRole.equals("Hr")){
				dis = request.getRequestDispatcher("hr");
				dis.include(request, response);
			}

			con.close();
			

		} catch (IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}