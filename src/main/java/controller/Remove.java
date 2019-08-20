package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Order;
import utility.Logger;

public class Remove extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int index = Integer.parseInt(request.getParameter("item_index"));
		
		Order order				= (Order) getServletContext().getAttribute("ORDER");
		
		Logger.log("Removing item from cart: [item-id]: " + order.getItemFromCart(index).getId() + ", [cart-index]: item " + index);
		order.removeItemFromCart(index);
		
		// go back to cart
		response.sendRedirect("cart.jsp");
	}

}
