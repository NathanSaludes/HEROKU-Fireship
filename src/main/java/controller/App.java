package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.History;
import model.Order;
import model.Product;
import utility.Config;
import utility.Logger;


public class App extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) == null) {
			Logger.log(" -------- FIRESHIP SHOPPING STARTED -------- ");
			
			// CREATE NEW SESSION
			HttpSession session = request.getSession();

			Logger.log("Initializing application configurations");
			Config c = new Config();
			
						
			// INITIALIZE BOJECTS
			Logger.log("Initializing objects");			
			ArrayList<Product> product_list = c.getProducts();
			History order_history 			= new History();
			Order order 					= new Order();
			
			// set vat rate
			order.setVatRate(Float.parseFloat(getServletContext().getInitParameter("VAT_RATE")));
			
			// BIND PRODUCT LIST AND ORDER HISTORY TO SERVLET CONTEXT
			ServletContext sc = getServletContext();
			sc.setAttribute("PRODUCTS_LIST", product_list);
			sc.setAttribute("ORDER_HISTORY", order_history);
			
			// BIND ORDER TO SESSION
			session.setAttribute("ORDER", order);
			
			response.sendRedirect("home.jsp");
		} else {
			response.sendRedirect("home.jsp");		
		}
	}
}
