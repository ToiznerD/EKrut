package Entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The OrderDetails class represents an order placed by a user. It contains information about the products
 * included in the order, the total price of the order, the store where the order was placed, the user who placed the order, 
 * the discount applied to the order (if any), the shipping method and the shipping address.
 */
public class OrderDetails implements Serializable {

	private static final long serialVersionUID = 6541830393027026985L;
	private ArrayList<OrderProduct> items;
	private int total_price, store_id, userId;
	private double discount;
	private String method, address;
	private boolean first_order = false;
	private boolean delayed_payment = false;

	
	/**
	 * @param userId the user id of the customer who order.
	 */
	public OrderDetails(int userId) {
		this.userId = userId;
		discount = 1.0;
		method = "Local";
		address = "";
	}

	/**
	 * @param id set UserId with a given user id.
	 */
	public void setUserId(int id) {
		userId = id;
	}

	/**
	 * @return customer id of this order customer.
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @return list of product (items in cart).
	 * @see         OrderProduct
	 */
	public ArrayList<OrderProduct> getItems() {
		return items;
	}

	/**
	 * @param items set list of product (items in cart) in order.
	 * @see         OrderProduct
	 */
	public void setItems(ArrayList<OrderProduct> items) {
		this.items = items;
	}

	/**
	 * @return total price of the order
	 */
	public int getTotal_price() {
		return total_price;
	}

	/**
	 * @param total_price  set the total price of the order.
	 */
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}

	/**
	 * @return store_id from which store the order has made.
	 */
	public int getStore_ID() {
		return store_id;
	}

	/**
	 * @param store_id  set the store_id of order.
	 */
	public void setStore_ID(int store_id) {
		this.store_id = store_id;
	}

	/**
	 * @return true if this order has a discount.
	 */
	public boolean hasDiscount() {
		if (discount != 1.0)
			return true;
		return false;
	}

	/**
	 * @param product a product from the catlog
	 * @return the price of a given product.
	 * @see		OrderProduct
	 */
	public double getProductPrice(OrderProduct product) {
		if (!hasDiscount())
			return product.getPrice();
		return product.getPrice() * (1 - discount) * (getFirstOrder() ? 0.8 : 1.0);
	}

	/**
	 * @return the total_price of an order including discount.
	 */
	public double getAfterDiscount() {
		if (!hasDiscount())
			return total_price * (getFirstOrder() ? 0.8 : 1.0);
		return total_price * (1 - discount) * (getFirstOrder() ? 0.8 : 1.0);
	}

	/**
	 * @return the discount in a double format.
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param discount a discount in decimal (0 - 1.0).
	 */
	public void setDiscount(double discount) {
		if (discount > 1.0 || discount < 0)
		this.discount = discount;
	}

	/**
	 * @return the method of the shipping.
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method of the order "Pickup" ,"Delivery","Local"
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the address of the user.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address of the order to be deliver to.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean getFirstOrder() {
		return first_order;
	}
	
	public void setFirstOrderTrue() {
		first_order = true;
	}

	public boolean isDelayed_payment() {
		return delayed_payment;
	}


	public void setDelayed_paymentTrue() {
		delayed_payment = true;
	}
	
	
	/*	ArrayList<OrderProduct> items;
		int total_price, store_id,userId;
		double discount;
		String method, address;*/

	/**
	 * Writes the object to the ObjectOutputStream. This method is used for serializing the OrderDetails object.
	 * @param out the ObjectOutputStream to write the object to
	 * @throws IOException if an I/O error occurs while writing the object
	 */

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

	/**
	 * Reads the object from the ObjectInputStream. This method is used for deserializing the OrderDetails object.
	 * @param in the ObjectInputStream to read the object from
	 * @throws IOException if an I/O error occurs while reading the object
	 * @throws ClassNotFoundException if the class of the object being read cannot be found
	 */
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
