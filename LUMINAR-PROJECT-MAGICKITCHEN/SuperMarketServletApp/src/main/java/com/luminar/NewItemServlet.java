package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/newitem")
public class NewItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");	

			out.println("<html><body>");
			out.println("<h1>Welcome " + name+"</h1><br><br>");

			out.println("<h1>New Item Details </h1>");

			out.print("<form name='new-item' action='saveitem' method='post'>");

			out.print("Item Name <input type='text' name='itemName'><br>");
			out.print("Item Quantity <input type='text' name='itemqty'><br>");

			out.print("<input type='submit' value='SAVE'>");
			out.print("</form>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
