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

@WebServlet("/students")
public class StudentsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/luminar_servlet";
	final String user = "root";
	final String password = "mysql";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);

			String sql = "select * from student";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			// response.getWriter().write("Hello");
			out.println("<html><body>");

			out.println("<h1>Student Details </h1>");

			out.println("<table border='1'>");

			out.println("<tr>");
			out.println("<th>ROLLNO</th>");
			out.println("<th>NAME</th>");
			out.println("<th>MARK</th>");
			
			
			out.println("</tr>");

			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getInt(1) + "</td>");
				out.println("<td>" + rs.getString(2) + "</td>");
				out.println("<td>" + rs.getFloat(3) + "</td>");
			
				out.println("</tr>");
			}

			out.println("</table>");
			out.println("</body></html>");
		} catch (IOException | SQLException | ClassNotFoundException ex) {

			ex.printStackTrace();

		}
	}

}
