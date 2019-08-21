package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product;
import utility.Logger;

public class Item extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get products and cart from application		
		ArrayList<Product> prodList		= (ArrayList<Product>) getServletContext().getAttribute("PRODUCTS_LIST");
		
		// get product id from url parameter
		String productId = request.getParameter("id");
		
		// if product list has no items redirect to homepage
		if(prodList.size() > 0) {
			Product item = null;
			
			for (int i=0; i<prodList.size(); i++) {
				if(prodList.get(i).getId().equals(productId)) {
					item = prodList.get(i);					
					break;
				}
			}
			
			if(item != null) {
				// forward request
				request.setAttribute("ITEM", item);
				request.getRequestDispatcher("item.jsp").forward(request, response);
			} else {
				Logger.log("ITEM DOES NOT EXIST");
				response.sendRedirect("home.jsp");
			}
			
		} else {
			response.sendRedirect("home.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
