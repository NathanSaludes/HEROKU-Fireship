package model;

import java.util.ArrayList;

public class History {
	private static ArrayList<Order> orders;
	
	public History() {
		orders = new ArrayList<Order>();
	}
	
	public void log(Order order) {
		orders.add(order);
	}
	
	public ArrayList<Order> getOrderHistory() {
		return this.orders;
	}
}
