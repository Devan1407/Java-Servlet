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


@WebServlet("/edititem")
public class EditItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/luminar_servlet";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");	
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			/*
			 * String id=request.getParameter("eid"); int
			 * itemId=Integer.parseInt(id);
			 */

			int id = Integer.parseInt(request.getParameter("eid"));
			

			pst = con.prepareStatement("select * from items where item_id="+id+"");
		//	System.out.println("select * from item where item_id="+id+"");
			rs = pst.executeQuery();

			response.setContentType("text/html");

			PrintWriter out = response.getWriter();

			out.println("<html><body>");
			
			out.println("<h1>Welcome " + name+"</h1><br><br>");

			out.println("<h1>Item Details </h1>");

			out.print("<form name='edit-item' action='updateitem' method='post'>");
			while (rs.next()) {
				out.print("<input type='hidden' name='itemId' value='"+rs.getInt(1)+"'><br>");
				out.print("<input type='text' name='itemName' value='"+rs.getString(2)+"'><br>");
				out.print("<input type='text' name='itemqty' value='"+rs.getInt(3)+"'><br>");
			}
			out.print("<input type='submit' value='UPDATE'>");
			out.print("</form>");
			out.println("</body>");
			out.println("</html>");
			out.close();
			con.close();
		} catch (IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}
