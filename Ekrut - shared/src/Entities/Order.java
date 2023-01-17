package Entities;

import java.io.Serializable;
import java.util.Date;
import java.sql.Time;

/**
 * Order class represents an order (Entity)
 * It has fields for the information of the order.
 */
public class Order implements Serializable{

	private static final long serialVersionUID = 2L;
	private int orderID;
	private String RecieverName, RecieverAddress, RecieverPhone;
	private String ShippingDate;
	private String ShippingTime;
	
	/**
	* Constructor for Order object.
	* @param orderID the ID of the order
	* @param RecieverName the name of the customer
	* @param RecieverAddress the destination address specified in the order
	* @param RecieverPhone the phone number of the customer
	* @param ShippingDate the date the order was placed
	* @param ShippingTime the time the order was placed
	*/
	public Order(int orderID, String RecieverName, String RecieverAddress, String RecieverPhone, Date ShippingDate, Time ShippingTime) {
		this.orderID = orderID;
		this.RecieverName = RecieverName;
		this.RecieverAddress = RecieverAddress;
		this.RecieverPhone=RecieverPhone;
		this.ShippingDate=ShippingDate.toString();
		this.ShippingTime=ShippingTime.toString();
	}

    /**
     * Gets the ID of the order.
     * @return int : order ID
     */
	public int getOrderID() {
		return orderID;
	}

    /**
     * Gets the name of the user.
     * @return String : User name
     */
	public String getRecieverName() {
		return RecieverName;
	}

    /**
     * Gets the address of the reciever.
     * @return String : reciever address
     */
	public String getRecieverAddress() {
		return RecieverAddress;
	}
	
    /**
     * Gets the phone of the user.
     * @return String : user phone
     */
	public String getRecieverPhone() {
		return RecieverPhone;
	}

    /**
     * Gets the order shipping date.
     * @return String : order shipping date
     */
	public String getShippingDate() {
		return ShippingDate;
	}

    /**
     * Gets the order shipping time.
     * @return String : order shipping time
     */
	public String getShippingTime() {
		return ShippingTime;
	}

	
}
