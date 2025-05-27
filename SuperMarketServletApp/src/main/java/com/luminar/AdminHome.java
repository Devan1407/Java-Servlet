package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminHome extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/luminar_servlet";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");	
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			
			pst = con.prepareStatement("select * from items"); 
			rs = pst.executeQuery();

			out.println("<html><body>");
			out.println("<h1>Welcome " + name+"</h1><br><br>");

			out.println("<h1>Item Details </h1>");
			
			out.println("<h1><a href='newitem'>New Item</a> </h1>");

			out.println("<table border='1'>");

			out.println("<tr>");
			out.println("<th>SINO</th>");
			out.println("<th>ITEM NAME</th>");
			out.println("<th>QUANTITY</th>");
			out.println("<th>EDIT</th>");
			out.println("<th>DELETE</th>");
			out.println("</tr>");
			int i = 1;
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + i + "</td>");
				out.println("<td>" + rs.getString(2) + "</td>");
				out.println("<td>" + rs.getInt(3) + "</td>");

				out.print("<td><a href='edititem?eid=" + rs.getInt(1) + "'>Edit</a></td>");
				out.print("<td><a href='deleteitem?did=" + rs.getInt(1) + "'>Delete</a></td>");
				out.println("</tr>");
				i++;
			}

			out.println("</table>");
			out.println("</body></html>");

			out.close();
			//con.close();

		}  catch (IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
}
