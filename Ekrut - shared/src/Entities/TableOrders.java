package Entities;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import javafx.util.converter.LocalTimeStringConverter;

public class TableOrders implements Serializable {

	private static final long serialVersionUID = 2L;
	private int OrderID, CustomerID;
	private String RecieverName, RecieverAddress, RecieverPhone, Status;
	private Date OrderDate, EstimatedDate;
	private Time OrderTime, EstimatedTime;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	public TableOrders(int CustomerID, int OrderID, String RecieverName, String RecieverAddress, String RecieverPhone, Date OrderDate,
			Time OrderTime, String Status, Date EstimatedDate, Time EstimatedTime) {
		this.CustomerID = CustomerID;
		this.OrderID = OrderID;
		this.RecieverName = RecieverName;
		this.RecieverAddress = RecieverAddress;
		this.RecieverPhone = RecieverPhone;
		this.OrderDate = OrderDate;
		this.OrderTime = OrderTime;
		this.Status = Status;
		this.EstimatedDate = EstimatedDate;
		this.EstimatedTime = EstimatedTime;
	}
	public int getCustomerID() {//erik
		return CustomerID;
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

	public Date getOrderDate() {
		return OrderDate;
	}

	public Time getOrderTime() {
		return OrderTime;
	}

	public String getStatus() {
		return Status;
	}

	public Date getEstimatedDate() {
		return EstimatedDate;
	}

	public Time getEstimatedTime() {
		return EstimatedTime;
	}

	public String getOrderDateAndTime() {
		return dateFormat.format(OrderDate) + ", " + timeFormat.format(OrderTime);
	}

	public String getEstimatedDelivery() {
		return EstimatedDate == null ? "Need approval" : dateFormat.format(EstimatedDate) + ", " + timeFormat.format(EstimatedTime);
	}

}
