package Entities;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderDetails {
	private static final DecimalFormat decimal = new DecimalFormat("0.00");
	private static final DecimalFormat decimalToInt = new DecimalFormat("0");
	ArrayList<OrderProduct> items;
	int total_price, store_id;
	double discount;
	String method, address;

	public OrderDetails() {
		store_id = 7;
		discount = 1.0;
	}

	public ArrayList<OrderProduct> getItems() {
		return items;
	}

	public void setItems(ArrayList<OrderProduct> items) {
		this.items = items;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}

	public String getAfterDiscountText() {
		if (discount == 1.0)
			return String.valueOf(getTotal_price());
		return decimal.format(total_price * (1 - discount));
	}

	public double getProductPrice(OrderProduct product) {
		return product.getPrice() * (1 - discount);
	}

	public double getAfterDiscount() {
		return total_price * (1 - discount);
	}

	public int getStore_ID() {
		return store_id;
	}

	public void setStore_ID(int store_id) {
		this.store_id = store_id;
	}

	public boolean hasDiscount() {
		if (discount != 1.0)
			return true;
		return false;
	}

	public String getDiscountText() {
		return decimalToInt.format(discount * 100) + "%";
	}

	public double getDiscount() {
		return 1 - discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
