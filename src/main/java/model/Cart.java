package model;
import java.util.ArrayList;import utility.Logger;

public class Cart {	ArrayList<Product> cart;		public Cart() {		cart = new ArrayList<Product>();	}		public ArrayList<Product> getCartItems() {		return cart;	}		public boolean addItem(Product item) {		if(cart != null) {			cart.add(item);				return true;		}		return false;	}	public boolean removeItem(int index) {		if(cart.remove(cart.get(index))) {			return true;		}				return false;	}		public Product getItem(int index) {		if(cart.get(index) != null) {			return cart.get(index);		}		return null;	}
}
