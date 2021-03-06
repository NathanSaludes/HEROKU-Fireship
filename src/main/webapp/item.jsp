<%@page import="utility.Logger"%>
<%@page import="model.Cart"%>
<%@page import="model.Order"%>
<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<% 
	Order order = (Order) session.getAttribute("ORDER");
	Product item = (Product) request.getAttribute("ITEM");
	
	if(order == null || item == null) {
		Logger.error("Session expired.");
		order = new Order();
		item = new Product();
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
    <link rel="shortcut icon" href="./assets/fire.png" type="image/x-icon">

    <title><%= item.getName() %></title>

    <!-- CSS Reset -->
    <link rel="stylesheet" href="./styles/reset.css">
    <!-- Page Style -->
    <link rel="stylesheet" href="./styles/item.css">
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
        <a class="return_link" href="./home.jsp">
            <img class="caret" src="./assets/Right Caret.svg" alt="Go Back to Shopping">
            Go back to shopping
        </a>
        <div class="product_view">
            <div class="image_container">
            	<% if(item.getItem_stocks() > 0) { %> 
                	<img class="product_image" src="./assets/item_images/<%= item.getImage_path() %>" alt="<%= item.getImage_path() %>">
               	<% } else { %>
               		<img class="product_image" src="./assets/item_images/soldout.png" alt="<%= item.getImage_path() %>">
               	<% } %>
            </div>
            <div class="product_details">
                <p class="product_name"><%= item.getName() %></p>
                <p class="product_price">&#8369;<%= String.format("%,.2f", item.getPrice()) %></p>
                <div class="rating">
                
                	<% for(int i=0; i < item.getRating(); ++i) { %>
                    	<img class="star" src="./assets/Star.svg" alt="rating">
                    <% } %>
                </div>
                
                
                <form action="Add" method="post">
                    <!-- HIDDEN FIELDS ------------------------------------------------------------------------- -->
                    <input type="hidden" name="item_image" 	value="<%= item.getImage_path() %>	">
                    <input type="hidden" name="item_stocks" value="<%=item.getItem_stocks() %>	">
                    <input type="hidden" name="item_rating" value="<%= item.getRating() %>		">
                    <input type="hidden" name="item_id" 	value="<%= item.getId() %>			">
                    <input type="hidden" name="item_name" 	value="<%= item.getName() %>		">
                    <input type="hidden" name="item_price" 	value="<%= item.getPrice() %>		">
                    <!-- --------------------------------------------------------------------------------------- -->
                    
                    <label class="quantity_label" for="item_quantity">Quantity: </label>
                    <% if(item.getItem_stocks() > 0) { %> 
	                    <input class="quantity_field" type="number" min="1" max="<%= item.getItem_stocks() %>" name="quantity" required="required" value="1">	                                  	
	               	<% } else { %>
	               		<input class="quantity_field" type="number" min="0" max="<%= item.getItem_stocks() %>" name="quantity" required="required" value="1" disabled="disabled">
	               	<% } %>
                    <br>
                    <% if(item.getItem_stocks() > 0) { %> 
	                    <input class="add_button" type="submit" value="Add to Cart">                	
	               	<% } else { %>
	               		<input class="add_button" type="submit" value="Add to Cart" disabled="disabled" style="background-color:#dcdcdc;">
	               	<% } %>
                </form>
                
            </div>
        </div>
    </div>
</body>
</html>