package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.CreditCardException;
import exceptions.OrderException;
import utility.Logger;

public class Order implements OnlineOrderingSystem {
	
	private Cart cart;
	private User user;
	
	private float vatRate;
	private String dateOrdered;
	private String orderCode;
	
	// COMPUTED FIELDS
	private double grossPrice;
	private double valueAddedTax;
	private double netPrice;
	
	public Order() {
		cart = new Cart();
		user = new User();
		vatRate = 0.00f;
		dateOrdered = null;
		orderCode = null;
		grossPrice = 0.00;
		valueAddedTax = 0.00;
		netPrice = 0.00;
	}
	
	/*----- ORDER OPERATIONS -----*/
	public void reset(float vat_rate) {
		cart 			= new Cart();	// INITIALIZE CART CONTAINER
		user 			= null;			// USER UNSET
		dateOrdered 	= null;			// DATE UNSET
		orderCode		= null;
		
		grossPrice 		= 0.00;			// GROSS RESET
		valueAddedTax 	= 0.00;			// VAT RESET
		netPrice 		= 0.00;			// NET RESET
		
		setVatRate(vat_rate);			// SET VAT RATE
		recaculatePrice();
	}

	public void recaculatePrice() {
		grossPrice = 0;
		valueAddedTax = 0;
		
		computeGrossPay();
		computeVAT();
		netPay();
	}

	public void setVatRate(float vat_rate) {
		this.vatRate = (vat_rate / 100);
	}
	
	// RETURN COMPUTED VALUES AS STRING
	public String getSubtotal() {
		return String.format("%,.2f", grossPrice);
	}
	public String getVAT() {
		return String.format("%,.2f", valueAddedTax);
	}
	public String getTotal() {
		return String.format("%,.2f", netPrice);
	}
		

	/*------------------------------------------------------------ INTERFACE METHODS ------------------------------------------------------------*/
	@Override
	public void validateCreditCard() {
		try {
			String creditCard = new StringBuilder(user.getCreditCard()).reverse().toString();
			
			if(creditCard.length() == 16) {
				int finalSum 	= 0;	// sum of both partial sumss
				int s1 			= 0;	// partial sum for numbers with even indexes
				int s2			= 0;	// partial sum for numbers with odd indexes
				int curr_num	= 0;	// current number being evaluated
				int doubled 	= 0;	// current number multiplied by two (or doubled)
				
				for (int i = 0; i < creditCard.length(); i++) {
					curr_num = Character.getNumericValue(creditCard.charAt(i));
					
					if(i % 2 == 0) {
						// if the index of a number is even, add the number directly to the sum
						s1 += curr_num;
						
					} else {
						// if the index of a number is odd, check if the double of a number is greater than 9 (which means a "two digit number")
						doubled = curr_num * 2;
						
						// check if the double of the current number is a 'two digit number'
						if(doubled > 9) { 																// if the double of a number is composed of 2 digits...
							int firstD 	= Integer.parseInt(Integer.toString(doubled).substring(0, 1)); 	// then get the first digit...
							int secondD = Integer.parseInt(Integer.toString(doubled).substring(1));		// then get the second digit...
							int tempSum = firstD + secondD; 											// then get the sum of the two digits...
							s2 += tempSum; 																// then store
							
						} else {
							s2 += doubled;
						}
					}
				}
				
				finalSum = s1 + s2;
				
				// if the final sum of the process is divisible by 10, the credit card is valid...
			    if (finalSum%10 == 0) {
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
			Logger.error(e.getMessage());
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
	}

	@Override
	public void generatePDFReceipt(String dest) {
		Document document = new Document();
		try {
			String fileName = "\\receipt.pdf";
			String path = dest + fileName;

			Document doc = new Document();
			PdfWriter.getInstance(doc, new FileOutputStream(path));
			doc.open();
			
			PdfPTable table = new PdfPTable(2);
			PdfPCell cell = new PdfPCell(new Phrase("Cell with colspan 2"));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			doc.add(table);
			doc.close();
			
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		}
	}
	/*------------------------------------------------------------ INTERFACE METHODS ------------------------------------------------------------*/	
	
	
	
	
	
	public Cart getCart() {
		return this.cart;
	}
	public User getUser() {
		return this.user;
	}
	public String getOrderDate() {
		return this.dateOrdered;
	}
	public String getOrderCode() {
		return this.orderCode;
	}
	public void setOrderDate(Date date) {
		this.dateOrdered = new SimpleDateFormat().format(new Date());
	}
	private void setOrderCode() {
		if(this.orderCode == null || this.orderCode.isEmpty()) {
			StringBuilder code = new StringBuilder();
			String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
			
			// generate a code
			code.append(user.getFirstName().charAt(0));
			int number = new Random().nextInt(9999);
			
			int index = 0; 
			for(int i=0; i < 10; i++) {
				index = (int) (AlphaNumericString.length() * Math.random());
				
				code.append(AlphaNumericString.charAt(index));
			}
			
			code.append(user.getLastName().charAt(0));
			code.append(number);
			code.append(user.getFirstName().length() + (int) Math.floor((Math.random() * 123456)));
			
			this.orderCode = code.toString().toUpperCase();			
		}
	}
	
	
	public boolean processOrder(User user) {
		
		try {
			// check if cart has items
			if(this.cart.hasItems()) {
				this.user = user;				// set new user for the current order
				this.validateCreditCard();		// validate user's credit card
				
				if(this.user.isCreditCardStatus()) {
					setOrderDate(new Date());
					setOrderCode();
					return true; // success
				}
				
			} else {
				throw new OrderException("You have no items on the cart");
			}
			
		} catch (OrderException e) {
			Logger.error(e.getMessage());
		}
		
		return false; // failed transaction
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public ArrayList<Product> updateStocks(ArrayList<Product> product_list) {
		Product item = null;
		String id = null;
		int quantity = 0;
		int stocks;
		
		Logger.log("Updating Product Stocks...");
		
		for(int index=0; index < cart.getCartItems().size(); ++index) { //for every item in the cart get its quantity
			item = this.cart.getItem(index);
			id = item.getId();
			quantity = item.getQuantity();
			
			Logger.log("SEARCHING PRODUCT LIST FOR [PRODUCT_ID: " + id + "]");
			
			// find the item in the product list
			for(Product product : product_list) {
				if(id.equals(product.getId())) {
					Logger.log("ITEM FOUND!");
					System.out.println("\nITEM:");
					System.out.println("ID: " + product.getId());
					System.out.println("Stocks: " + product.getItem_stocks() + "\n");
					stocks = product.getItem_stocks() - quantity;
					product.setItem_stocks(stocks);
					Logger.log("Updated stock quantity of item [" + product.getId() + "]: " + product.getItem_stocks() + " left\n");
					break;
				}
			}
		}
		
		return product_list;
	}
	
	public void logProductsStock(ArrayList<Product> products) {
		// display all the product stocks
		System.out.println("\nProduct List ==================================================================");
		for(Product p : products) {
			System.out.println("ID: " + p.getId());
			System.out.println("Stocks Left: " + p.getItem_stocks() + "\n");
		}
		System.out.println("===============================================================================");
	}
}
