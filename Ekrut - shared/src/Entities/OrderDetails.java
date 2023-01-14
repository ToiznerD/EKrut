package Entities;

import java.util.ArrayList;

public class OrderDetails {
	ArrayList<OrderProduct> items;
	int total_price, store_id;
	double discount;
	String method, address;
	
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
	
	public int getStore_ID() {
		return store_id;
	}
	
	public void setStore_ID(int store_id) {
		this.store_id = store_id;
	}
	
	public double getDiscount() {
		return discount;
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
