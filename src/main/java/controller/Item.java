package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product;

public class Item extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get products and cart from application		
		ArrayList<Product> prodList		= (ArrayList<Product>) getServletContext().getAttribute("PRODUCTS_LIST");
		
		// get product id from url parameter
		String productId = request.getParameter("id");
		
		// products fields
		String item_id = null;
		String item_name = null;
		String imageName = null;
		double item_price = 0;
		int item_rating = 0;
		int item_stock = 0;
		
		// if product list has no items redirect to homepage
		if(prodList.size() > 0) {
			
			for(Product p : prodList) {
				// get item
				if(p.getId().equals(productId)) {
					item_id		= p.getId();
					item_name	= p.getName();
					item_price	= p.getPrice();
					imageName	= p.getImage_path();
					item_rating	= p.getRating();
					item_stock	= p.getItem_stocks();
					
					break;
				}
			}
			
			Product item = new Product(item_id, item_name, imageName, item_price, item_stock, item_rating);
			
			// forward request
			request.setAttribute("ITEM", item);
			request.getRequestDispatcher("item.jsp").forward(request, response);
			
		} else {
			response.sendRedirect("home.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
