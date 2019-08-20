package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cart;
import model.History;
import model.Order;
import model.Product;
import utility.Config;
import utility.Logger;


public class App extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.log(" --- FIRESHIP SHOPPING STARTED --- ");
		Logger.log("INITIALIZING APP CONFIGURATION");
		
		Config c = new Config();
		
		Logger.log("INITIALIZING OBJECTS");
		
		// INITIALIZE BOJECTS
		ArrayList<Product> product_list = c.getProducts();
		History order_history = new History();
		Order order = new Order();
		order.setVatRate(Float.parseFloat(getServletContext().getInitParameter("VAT_RATE")));
		
		// BIND OBJECTS TO SERVLET CONTEXT
		ServletContext sc = getServletContext();
		sc.setAttribute("PRODUCTS_LIST", product_list);
		sc.setAttribute("ORDER_HISTORY", order_history);
		sc.setAttribute("ORDER", order);
		
		response.sendRedirect("home.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
