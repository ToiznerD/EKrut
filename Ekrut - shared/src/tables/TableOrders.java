package tables;

import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class TableOrders implements Serializable{

	private static final long serialVersionUID = 2L;
	private int orderID;
	private String RecieverName, RecieverAddress, RecieverPhone;
	private String ShippingDate;
	private String ShippingTime;
//	private enum status{Pending,Approve};
	
	public TableOrders(int orderID,String RecieverName, String RecieverAddress, String RecieverPhone, Date ShippingDate, Time ShippingTime) {
		this.orderID = orderID;
		this.RecieverName = RecieverName;
		this.RecieverAddress = RecieverAddress;
		this.RecieverPhone=RecieverPhone;
		this.ShippingDate=ShippingDate.toString();
		this.ShippingTime=ShippingTime.toString();
		
	}

	
	public int getOrderID() {
		return orderID;
	}

	public String getRecieverName() {
		return RecieverName;
	}

	public String getRecieverAddress() {
		return RecieverAddress;
	}

	public String getRecieverPhone() {
		return RecieverPhone;
	}

	public String getShippingDate() {
		return ShippingDate;
	}

	public String getShippingTime() {
		return ShippingTime;
	}

	
}
