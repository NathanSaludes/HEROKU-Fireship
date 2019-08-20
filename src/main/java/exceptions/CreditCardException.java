package exceptions;

public class CreditCardException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public CreditCardException(String message) {
		super(message);
	}
}
