package model;

public class User {
	private String fullName;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String shippingAddress;
	
	private String creditCard;
	private boolean creditCardStatus;
	
	public User() {
		this.fullName 			= null;
		this.firstName 			= null;
		this.lastName 			= null;
		this.middleInitial 		= null;
		this.shippingAddress 	= null;
		this.creditCard			= null;
		this.creditCardStatus	= false;
	}

	public User(String fullName, String firstName, String lastName, String middleInitial, String shippingAddress, String creditCard) {
		this.firstName 			= firstName;
		this.lastName 			= lastName;
		this.middleInitial 		= middleInitial;
		this.fullName 			= firstName + " " + middleInitial + ". " + lastName;
		this.shippingAddress 	= shippingAddress;
		
		// set credit card (filter)
		setCreditCardNumber(creditCard);
	}

	// GETTERS
	public String getFullName() {
		return fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public boolean isCreditCardStatus() {
		return creditCardStatus;
	}
	
	
	
	
	
	
	
	
	
	
	public void setCreditCardNumber(String credit) {
		credit = credit.replaceAll(" ", "");	// remove whitespaces
		this.creditCard = credit;
	}

	public void setCreditCardStatus(boolean status) {
		this.creditCardStatus = status;
	}
	
	
}
