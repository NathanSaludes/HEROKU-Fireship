package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cart;
import model.Order;
import utility.Logger;

public class Remove extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int index = Integer.parseInt(request.getParameter("item_index"));
		
		Order order	= (Order) request.getSession().getAttribute("ORDER");
		
		Logger.log("Removing item from cart: [item-id]: " + order.getCart().getItem(index).getId() + ", [index]: " + index);
		
		if(order.getCart().removeItem(index)) {
			order.recaculatePrice();
			response.sendRedirect("cart.jsp");			
		}
		
	}

}
