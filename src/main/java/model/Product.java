package model;

public class Product {
	private String id;
	private String name;
	private String image_path;
	private double price;
	private int item_stocks;
	private int quantity;
	private int rating;
	
	public Product() {
		this.id 			= null;
		this.name 			= null;
		this.image_path		= null;
		this.price 			= 0.00;
		this.item_stocks 	= 0;
		this.quantity 		= 0;
		this.rating			= 0;
	}
	
	public Product(String id, String name, String image_path, double price, int item_stocks, int rating) {
		this.id 			= id;
		this.name 			= name;
		this.image_path		= image_path;
		this.price 			= price;
		this.item_stocks 	= item_stocks;
		this.rating			= rating;
		this.quantity		= 0;
	}

	public Product(String id, String name, String image_path, double price, int item_stocks, int rating, int quantity) {
		this.id 			= id;
		this.name 			= name;
		this.image_path		= image_path;
		this.price 			= price;
		this.item_stocks 	= item_stocks;
		this.quantity 		= quantity;
		this.rating			= rating;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImage_path() {
		return image_path;
	}

	public double getPrice() {
		return price;
	}

	public int getItem_stocks() {
		return item_stocks;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getRating() {
		return rating;
	}

	
	
	
	
	
	
	
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setItem_stocks(int item_stocks) {
		this.item_stocks = item_stocks;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
