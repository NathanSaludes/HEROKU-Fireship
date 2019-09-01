<%@page import="utility.Logger"%>
<%@page import="model.Cart"%>
<%@page import="model.Order"%>
<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	ArrayList<Product> products = (ArrayList<Product>) application.getAttribute("PRODUCTS_LIST");
	Order order = (Order) session.getAttribute("ORDER");
	
	if(order == null || products == null) {
		Logger.error("Session expired.");
		products = new ArrayList<Product>();
		order = new Order();
		session.invalidate();
		response.sendRedirect("App");
	}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="shortcut icon" href="./assets/fire.png" />

    <title>Fireship | Home</title>

    <!-- CSS Reset -->
    <link rel="stylesheet" href="./styles/reset.css" lang="text/css">
    <!-- Page Style -->
    <link rel="stylesheet" href="./styles/home.css">
    
    <!-- Font Awesome Kit -->
    <script src="https://kit.fontawesome.com/24fa509132.js"></script>

</head>
<body>

    <!-- Navigation bar -->
    <nav>
        <a href="./home.jsp">
            <img class="fireship_logo" src="./assets/fireship.png" alt="Fireship">
        </a>
        
        <a class="cart_icon" href="cart.jsp">
        
        	<%-- SHOPPING CART STATE RENDERING | (Gray : Empty Cart) & (Yellow : Items present) ---------------------------------- --%>
	        <% if(order.getCart().hasItems()) { %>
	        	<i class="fas fa-shopping-cart cart_icon" style="color: #ffcd43"></i>
	        <% } else { %>
	            <i class="fas fa-shopping-cart cart_icon"></i>        
	        <% } %>
	        <%-- ----------------------------------------------------------------------------------------------------------------- --%>
	        
        </a>
    </nav>

    <!-- Wrapper Container-->
    <div class="wrapper_container">
    
	    <div class="banner_container">
	        <img id="Banner" src="./assets/Hero_Image.png" alt="Free Shipping! Banner">    
	    </div>

        <div class="item_list_container">

			<%-- RENDER ALL PRODUCTS --------------------------------------------------------------------------------------------- --%>
			<% for(Product product : products) { %>
	            <!-- Loop -->
	            <a href="Item?id=<%= product.getId() %>">
	                <div class="item"> <!-- ITEM CARD -->
	                    <div class="product_image_container">
	                    	<% if(product.getItem_stocks() > 0) { %>                    
	                        	<img class="product_image" src="./assets/item_images/<%= product.getImage_path() %>" alt="<%= product.getName() %>">
	                        <% } else { %>
	                        	<img class="product_image" src="./assets/item_images/soldout.png" alt="<%= product.getName() %>">
	                        <% } %>
	                    </div>
	                    <div class="product_info">
	                        <p class="product_name"><%= product.getName() %></p>
	                        <p class="product_price">&#8369;<%= String.format("%,.2f", product.getPrice()) %></p>
	                        <div class="rating">
	                        <% for(int j=0; j < product.getRating() ; ++j) { %>
	                           	<img class="star" src="assets/Star.svg" alt="rating">
	                        <% } %>
	                        </div>
	                        
	                    </div>
	                </div> <!-- ITEM CARD -->
	            </a>
	        <% } %>
	        <%-- ---------------------------------------------------------------------------------------------------------------- --%>
        
        </div> <!-- ITEM LIST CONTAINER -->
        <p class="end">- End of Product Feed -</p>
    </div>
</body>
</html>