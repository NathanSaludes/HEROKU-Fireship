<%@page import="utility.Logger"%>
<%@page import="model.Order"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
	Order order = (Order) session.getAttribute("ORDER");
	
	if(order == null) {
		Logger.error("Session expired.");
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
	
	<title>Fireship | Invalid</title>
	
	<!-- styles -->
	<link rel="stylesheet" href="./styles/reset.css">
	<link rel="stylesheet" href="./styles/invalid.css">
</head>
<body>
	
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
	
	<div class="wrapper_container">
		<div class="image_container">
			<img class="shopping_bag_graphic" src="./assets/Shopping Bag Invalid.png" alt="Shopping Bag Graphic Invalid">
		</div>
		<div class="banner">
			<img class="banner" src="./assets/Invalid Transaction.png">
			<p class="error">The credit card you provided is invalid!</p>
		</div>

		<div class="process_buttons_container">
			<a href="cart.jsp">
				<button class="back_button">back to cart</button>
			</a>
			or	
			<a href="Cancel">
				<button class="cancel_order_button">cancel order</button>
			</a>
		</div>
	</div>
	
	
	
	<script src="https://kit.fontawesome.com/24fa509132.js"></script>
</body>
</html>