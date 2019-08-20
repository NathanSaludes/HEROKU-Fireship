<%@page import="model.Order"%>
<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<% 
	// get cart items
	Order order 			= (Order) application.getAttribute("ORDER");
	ArrayList<Product> cart = order.getOrderCart();
%>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="shortcut icon" href="./assets/fire.png" type="image/x-icon">

    <title>Fireship | Cart</title>

    <!-- CSS Reset -->
    <link rel="stylesheet" href="./styles/reset.css">
    <!-- Page Style -->
    <link rel="stylesheet" href="./styles/cart.css">
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
	        <% if(cart.size() > 0) { %>
	        	<i class="fas fa-shopping-cart cart_icon" style="color: #ffcd43"></i>
	        <% } else { %>
	            <i class="fas fa-shopping-cart cart_icon"></i>        
	        <% } %>
	        <%-- ----------------------------------------------------------------------------------------------------------------- --%>
	        
        </a>
    </nav>

    <!-- Wrapper Container-->
    <div class="wrapper_container">
        <h1>Shopping Cart</h1>
        <div class="cart_list">
        
        	<% int index = 0; %>
            <% for(Product p : cart) {
            %>
	            <div class="item">
	                <div class="item_image_container">
	                    <a href="item.jsp?id=<%= p.getId() %>">
	                        <img class="product_image" src="./assets/item_images/<%= p.getImage_path() %>" alt="<%= p.getName() %>">
	                    </a>
	                </div>
	                <div class="item_info">
	                    <a href="item.jsp?id=<%= p.getId() %>">
	                        <p class="product_name"><%= p.getName() %></p>
	                    </a>
	                    <div class="rating">
	                    	<% for(int i=0; i < p.getRating(); ++i) { %>
	                        	<img class="star" src="./assets/Star.svg" alt="rating">
	                        <% } %>
	                    </div>
	                    <p class="item_price">&#8369;<%= String.format("%,.2f", p.getPrice()) %></p>
	                    <p class="item_quantity"><%= p.getQuantity() %></p>
	                    
	                    <form action="Remove" method="post">
	                    	<input type="hidden" name="item_index" value="<%= index %>">
	                        <button class="removeButton">remove item</button>	                    
	                    </form>
	                </div>
	            </div>
	            
	        <% ++index;%>
            <% } %>
            
            <p class="end">- End of Cart-</p>
        </div>



        <div class="billing_info">
            <form action="Process" method="post">
                <p class="label">Customer Details</p>
                
                <!-- FIRSTNAME ----------------------------------------------------------------------------------------------------- -->
                <input type="text" placeholder="Firstname" name="user_firstname" required="required">
                <br>
                
                <!-- LASTNAME ------------------------------------------------------------------------------------------------------ -->
                <input type="text" placeholder="Lastname" name="user_lastname" required="required">
                <br>
                
                <!-- SHIPPING ADDRESS ---------------------------------------------------------------------------------------------- -->
                <textarea id="shipping_address" placeholder="Shipping Address..." name="shipping_address" required="required"></textarea>
                <br>
    
                <!-- CREDIT CART NUMBER -------------------------------------------------------------------------------------------- -->
                <p class="label">Payment</p>
                <input id="creditCardNumber" type="text" placeholder="Credit Card Number" name="creditCard" required="required">

                <p class="label">Order Summary</p>
                <div class="subtotal">
                    <span>Subtotal (<%= cart.size() %> <% if (cart.size() > 1) {%>items)<% } else { %> item) <% } %></span>
                    <p id="subtotal_value">&#8369;<%= order.getSubtotal() %></p>
                    <span>VAT</span>
                    <p id="subtotal_value">&#8369;<%= order.getVAT() %></p>
                    <span id="total">TOTAL</span>
                    <p id="total_price">&#8369;<%= order.getTotal() %></p>
                </div>
                <br>
                <input id="pay_button" type="submit" value="Pay">
            </form>
        </div>
    </div>
</body>
</html>