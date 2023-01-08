package Entities;

import javafx.beans.property.SimpleIntegerProperty;

public class OrderProduct {
	// image
	private int productID;
	private String name;
	private int price, quant;
	private SimpleIntegerProperty cartQuant = new SimpleIntegerProperty(0);
	// quantity

	public OrderProduct(int productID, String name, int price, int quant) {
		this.productID = productID;
		this.name = name;
		this.price = price;
		this.quant = quant;
	}

	public int getProductID() {
		return productID;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getQuant() {
		return quant;
	}

	public int getCartQuant() {
		return cartQuant.get();
	}

	public SimpleIntegerProperty getCartQuantProperty() {
		return cartQuant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
	}

	public boolean addToCart() {
		if (cartQuant.get() < quant) {
			cartQuant.set(cartQuant.get() + 1);
			return false;
		}
		return true;
	}

	public int removeFromCart() {
		if (cartQuant.get() > 0) {
			cartQuant.set(cartQuant.get() - 1);
			return cartQuant.get();
		}
		return 0;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
