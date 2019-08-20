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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName;
		String lastName;
		String creditCardNumber;
		String shippingAddress;
		
		firstName = request.getParameter("user_firstname").trim();
		lastName = request.getParameter("user_lastname").trim();
		shippingAddress = request.getParameter("shipping_address").trim();
		creditCardNumber = request.getParameter("creditCard").trim();
		
		User newUser = new User(firstName, lastName, shippingAddress, creditCardNumber);
		
		Logger.log("PROCESSING ORDER...");
		
		// get the order object
		Order order = (Order) getServletContext().getAttribute("ORDER");
				
		if(order.processOrder(newUser)) {
			Logger.log("ORDER TRANSACTION SUCCESS. [Order date]: " + order.getOrderDate());
			ArrayList<Product> product_list = (ArrayList<Product>) getServletContext().getAttribute("PRODUCTS_LIST");
			History order_history = (History) getServletContext().getAttribute("ORDER_HISTORY");
			
			// record/add order to history
			order_history.log(order);
		
			// update stocks of each product
			product_list = order.updateStocks(product_list);
			
			// reset order object to prepare for the next order
			Order newOrder = new Order();
			newOrder.reset(Float.parseFloat(getServletContext().getInitParameter("VAT_RATE")));
			
			
			// TEST =================================================================================================================================================
			// new OrderHistoryTesting().main(order_history);
			// TEST =================================================================================================================================================
			
			
			// set the new order object to the servlet context else
			// it will keep the data of the previous order regardless if it's valid or invalid
			getServletContext().setAttribute("ORDER", newOrder);
			getServletContext().setAttribute("PRODUCTS_LIST", product_list);
			getServletContext().setAttribute("ORDER_HISTORY", order_history);
			
			response.sendRedirect("home.jsp");
			
		} else {
			Logger.log("ORDER FAILED");
			
			// redirect Invalid.jsp (go back to cart or cancel?)
			response.sendRedirect("cart.jsp");
		}
	}

}
