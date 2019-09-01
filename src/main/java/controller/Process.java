package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.History;
import model.Order;
import model.Product;
import model.User;
import utility.Logger;

public class Process extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String firstName 			= request.getParameter("user_firstname").trim();
		String lastName 			= request.getParameter("user_lastname").trim();
		String shippingAddress 		= request.getParameter("shipping_address").trim();
		String creditCardNumber 	= request.getParameter("creditCard").trim();
		
		User newUser = new User(firstName, lastName, shippingAddress, creditCardNumber);
		
		Logger.log("PROCESSING ORDER...");
		
		// get the order object
		Order order = (Order) request.getSession().getAttribute("ORDER");
		
		if(order.processOrder(newUser)) {
			Logger.log("ORDER TRANSACTION SUCCESS. [Order date]: " + order.getOrderDate());
			ArrayList<Product> product_list = (ArrayList<Product>) getServletContext().getAttribute("PRODUCTS_LIST");
			History order_history = (History) getServletContext().getAttribute("ORDER_HISTORY");
			
			// create PDF file for user receipt
			order.generatePDFReceipt();
			
			// record/add recent order to history
			order_history.log(order);
		
			// update stocks of each product
			product_list = order.updateStocks(product_list);
			
			
			// reset order object to prepare for the next order
			Order newOrder = new Order();
			newOrder.reset(Float.parseFloat(getServletContext().getInitParameter("VAT_RATE")));			
			
			// attach new order object to session
			request.getSession().setAttribute("ORDER", newOrder);
			
			
			getServletContext().setAttribute("PRODUCTS_LIST", product_list);
			getServletContext().setAttribute("ORDER_HISTORY", order_history);
			
			response.setStatus(301);
			request.setAttribute("ORDER", order);
			request.getRequestDispatcher("success.jsp").forward(request, response);
			
		} else {
			Logger.log("ORDER PROCESS FAILED");
			response.sendRedirect("invalid.jsp");
		}
	}

}
