package Entities;
import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

import javafx.util.converter.LocalTimeStringConverter;

public class TableOrders implements Serializable{

	private static final long serialVersionUID = 2L;
	private int OrderID;
	private String RecieverName, RecieverAddress, RecieverPhone,ShippingDate, ShippingTime, Status;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
	
	public TableOrders(int OrderID,String RecieverName, String RecieverAddress,
				String RecieverPhone, Date ShippingDate, Time ShippingTime, String Status) {
		this.OrderID = OrderID;
		this.RecieverName = RecieverName;
		this.RecieverAddress = RecieverAddress;
		this.RecieverPhone = RecieverPhone;
		this.ShippingDate = dateFormat.format(ShippingDate);
		this.ShippingTime = timeFormat.format(ShippingTime);
		this.Status = Status;
	}

	
	public int getOrderID() {
		return OrderID;
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
	
	public String getStatus() {
		return Status;
	}
	
	
}
