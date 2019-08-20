package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import exceptions.CreditCardException;

public class Order implements model.OnlineOrderingSystem {
	
	private Cart cart;
	private User user;
	
	private float vatRate;
	private String dateOrdered;
	
	// COMPUTED FIELDS
	private double grossPrice;
	private double valueAddedTax;
	private double netPrice;
	
	public Order() {
		cart = new Cart();
		user = new User();
		vatRate = 0.00f;
		dateOrdered = null;
		grossPrice = 0.00;
		valueAddedTax = 0.00;
		netPrice = 0.00;
	}
	
	public void reset(float vat_rate) {
		cart 			= new Cart();	// INITIALIZE CART CONTAINER
		user 			= null;			// USER UNSET
		dateOrdered 	= null;			// DATE UNSET
		
		grossPrice 		= 0.00;			// GROSS RESET
		valueAddedTax 	= 0.00;			// VAT RESET
		netPrice 		= 0.00;			// NET RESET
		
		setVatRate(vat_rate);			// SET VAT RATE
		recaculatePrice();
	}

	private void recaculatePrice() {
		grossPrice = 0;
		valueAddedTax = 0;
		
		computeGrossPay();
		computeVAT();
		netPay();
	}

	public void setVatRate(float vat_rate) {
		this.vatRate = (vat_rate / 100);
	}

	@Override
	public void validateCreditCard() {
		try {
			String creditCard = user.getCreditCard();
			
			if(creditCard.length() == 16) {
				int sum = 0;
				int digit = 0;
				
				for (int i = 0; i < creditCard.length(); i++) {
					digit = Character.getNumericValue(creditCard.charAt(i));
					
					if(i % 2 == 0) {
						if((digit * 2) > 9) { 															// if the double of a number is composed of 2 digits...
			        		int firstD = Integer.parseInt(Integer.toString(digit * 2).substring(0, 1)); // then get the first digit...
			        		int secondD = Integer.parseInt(Integer.toString(digit * 2).substring(1));	// then get the second digit...
			        		int tempSum = firstD + secondD; 											// then get the sum of the two digits...
			        		sum += tempSum; 															// then store
			        		
			        	} else {
			        		sum += digit;
			        	}
					} else {
						sum += digit;
					}
				}
				
				// if the final sum of the process is divisible by 10, the credit card is valid...
			    if (sum%10 == 0) {
			    	user.setCreditCardStatus(true);
			    } else {
			    	user.setCreditCardStatus(false);
			    	throw new CreditCardException("INVALID CREDIT CARD!");
			    } 
			} else {
				throw new CreditCardException("INVALID CREDIT CARD LENGTH!");
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (CreditCardException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void computeGrossPay() {
		for(Product item : cart.getCartItems()) {
			grossPrice += (item.getPrice() * item.getQuantity());
		}
		
	}

	@Override
	public void computeVAT() {
		valueAddedTax = grossPrice * vatRate;
	}

	@Override
	public void netPay() {
		netPrice = grossPrice + valueAddedTax;		
	}

	@Override
	public void printPDFSalesReport() {
		// TODO Auto-generated method stub
	}

	@Override
	public void generatePDFReceipt() {		
	}
	
	
	
	
	
	public ArrayList<Product> getOrderCart() {
		return cart.getCartItems();
	}
	public User getOrderUser() {
		return this.user;
	}
	public String getOrderDate() {
		return this.dateOrdered;
	}
	public void setOrderDate(Date date) {
		this.dateOrdered = new SimpleDateFormat().format(new Date());
	}
	
	
	
	
	
	
	public boolean processOrder(User user) {
		this.user = user;				// set new user for the current order
		this.validateCreditCard();		// validate user's credit card
		
		if(this.user.isCreditCardStatus()) {
			setOrderDate(new Date());
			return true; // success
		}
		
		return false; // failed transaction
	}
}
