package model;

public interface OnlineOrderingSystem {
	void validateCreditCard(); //Luhn algo to check card
	void computeGrossPay();
	void computeVAT();
	void netPay();
	void printPDFSalesReport(); //terminates the app, after checkout
	void generatePDFReceipt(String dest); //pdf as sales receipt
}
