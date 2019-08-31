package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Order;
import utility.Logger;

public class Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Order newOrder = new Order();
		newOrder.reset(Float.parseFloat(getServletContext().getInitParameter("VAT_RATE")));

		request.getSession().setAttribute("ORDER", newOrder);
		
		Logger.log("Order Cancelled.");
		response.sendRedirect("home.jsp");
	}
}
