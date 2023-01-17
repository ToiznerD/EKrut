package Entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderDetails implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6541830393027026985L;
	private ArrayList<OrderProduct>  items;
	private int total_price, store_id,userId;
	private double discount;
	private String method, address;
	private boolean first_order = false;

	public OrderDetails(int userId) {
		this.userId = userId;
		discount = 1.0;
		method = "Local";
		address = "";
	}

	public void setUserId(int id) {
		userId = id;
	}
	public int getUserId() {
		return userId;
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

	public double getProductPrice(OrderProduct product) {
		if (!hasDiscount())
			return product.getPrice();
		return product.getPrice() * (1 - discount) * (getFirstOrder() ? 0.8 : 1.0);
	}

	public double getAfterDiscount() {
		if (!hasDiscount())
			return total_price * (getFirstOrder() ? 0.8 : 1.0);
		return total_price * (1 - discount) * (getFirstOrder() ? 0.8 : 1.0);
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
	
	public boolean getFirstOrder() {
		return first_order;
	}
	
	public void setFirstOrderTrue() {
		first_order = true;
	}

	/*	ArrayList<OrderProduct> items;
		int total_price, store_id,userId;
		double discount;
		String method, address;*/
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeInt(total_price);
		out.writeInt(store_id);
		out.writeInt(userId);
		out.writeDouble(discount);
		out.writeUTF(address);
		out.writeUTF(method);
		 OrderProduct[] itemsArray = items.toArray(new OrderProduct[items.size()]);
		out.writeObject(itemsArray);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		total_price = in.readInt();
		store_id = in.readInt();
		userId = in.readInt();
		discount = in.readDouble();
		address = in.readUTF();
		method = in.readUTF();
		OrderProduct[] itemsArray = (OrderProduct[]) in.readObject();
		items = new ArrayList<>(Arrays.asList(itemsArray));
	
	}

}
