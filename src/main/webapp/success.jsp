<%@page import="model.Product"%>
<%@page import="model.User"%>
<%@page import="model.Cart"%>
<%@page import="utility.Logger"%>
<%@page import="model.Order"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<% 
	Order order = (Order) request.getAttribute("ORDER");
	Cart cart = new Cart();
	User user = new User();
	
	if(order == null) {
		Logger.error("You haven't made any orders and why the heck are you accessing the success page?");
		response.sendRedirect("home.jsp");
	} else {
		cart = order.getCart();
		user = order.getUser();
	}
%>
    
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<link rel="shortcut icon" href="./assets/fireship.png" type="image/x-icon">

	<title>Fireship | Success</title>

	<link rel="stylesheet" href="./styles/reset.css">
	<link rel="stylesheet" href="./styles/success.css">
</head>

<body>

	<nav>
		<a href="home.jsp">
			<img class="fireship_logo" src="./assets/Fireship.svg" alt="Fireship">
		</a>
		<a class="cart_icon" href="./cart.html">
			<i class="fas fa-shopping-cart cart_icon"></i>
		</a>
	</nav>

	<div class="wrapper_container">
		<div class="image_container">
			<img class="shopping_bag_graphic" src="./assets/Shopping Bag Graphics.png" alt="Thank You For Shopping With Us!">
		</div>
		<div class="banner">
			<img class="banner" src="./assets/Thank you for shopping with us!.png">
			<a href="home.jsp" class="go_back">Click here to go back to shopping</a>
		</div>

		<div class="receipt">
			<div class="header">
					<p id="label">Reciept for:</p>
					<p id="user_name"><%= user.getFullName() %></p>
			</div>

			<div class="receipt_content">
					<!-- DATE -->
					<div id="date" class="order_field">
						<p class="label">Date Ordered:</p>
						<p class="field_value"><%= order.getOrderDate() %></p>
					</div>

					<!-- ORDER ID -->
					<div id="order_id" class="order_field">
						<p class="label">Order ID:</p>
						<p class="field_value"><%= order.getOrderCode() %></p>
					</div>

					<!-- SHIPPING ADDRESS -->
					<div id="shipping_address" class="order_field">
						<p class="label">Shipping Address:</p>
						<p class="field_value"><%= user.getShippingAddress() %></p>
					</div>

					<!-- TOTAL PRICE -->
					<div id="total_price" class="order_field">
						<p class="label">Total Price:</p>
						<p class="field_value" id="order_total_value">&#8369;<%= order.getTotal() %>
						</p>
					</div>
			</div>


			<div class="item_list">
				<p id="item_list_header">ITEM LIST</p>
				
				<% for(Product item : cart.getCartItems()) { %>
					<div class="item">
						<p class="item_name"><%= item.getName() %></p>
						<p class="item_quantity">
							<span class="label">Quantity:</span>
							<span class="value"><%= item.getQuantity() %></span>
						</p>
						<p class="item_price">
							<span class="label">Item Price:</span>
							<span class="value">&#8369;<%= item.getPrice() %></span>
						</p>
					</div>
				<% } %>
			</div>



		</div>

		<!-- DOWNLOAD BUTTON -->
		<button id="buttonGeneratePDF">
			<i class="fas fa-file-pdf"></i> Download PDF
		</button>
	</div>

	<script src="https://kit.fontawesome.com/24fa509132.js"></script>
</body>

</html>