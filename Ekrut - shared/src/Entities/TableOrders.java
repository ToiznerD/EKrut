package Entities;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TableOrders{

	private int OrderID, CustomerID;
	private String RecieverName, RecieverAddress, RecieverPhone, Status;
	private LocalDate OrderDate, EstimatedDate;
	private LocalTime OrderTime, EstimatedTime;
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");
	private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

	public TableOrders(int CustomerID, int OrderID, String RecieverName, String RecieverAddress, String RecieverPhone,
			Date OrderDate, Time OrderTime, String Status, Date EstimatedDate, Time EstimatedTime) {
		this.CustomerID = CustomerID;
		this.OrderID = OrderID;
		this.RecieverName = RecieverName;
		this.RecieverAddress = RecieverAddress;
		this.RecieverPhone = RecieverPhone;
		this.OrderDate = convertdate(OrderDate);
		this.OrderTime = converttime(OrderTime);
		this.Status = Status;
		this.EstimatedDate = convertdate(EstimatedDate);
		this.EstimatedTime = converttime(EstimatedTime);
	}

	public LocalDate convertdate(Date date) {
		if (date == null)
			return null;
		return new Timestamp(date.getTime()).toLocalDateTime().toLocalDate().plusDays(1);
	}

	public LocalTime converttime(Time time) {
		if (time == null)
			return null;
		return new Timestamp(time.getTime()).toLocalDateTime().toLocalTime().plusMinutes(210);
	}

	public int getCustomerID() {// erik
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

	public LocalDate getOrderDate() {
		return OrderDate;
	}

	public LocalTime getOrderTime() {
		return OrderTime;
	}

	public String getStatus() {
		return Status;
	}

	public LocalDate getEstimatedDate() {
		return EstimatedDate;
	}

	public LocalTime getEstimatedTime() {
		return EstimatedTime;
	}

	public String getOrderDateAndTime() {
		return OrderDate.format(dateFormat) + ", " + OrderTime.format(timeFormat);
	}

	public String getEstimatedDelivery() {
		return EstimatedDate == null ? "Need approval"
				: EstimatedDate.format(dateFormat) + ", " + EstimatedTime.format(timeFormat);
	}

	public void setEstimatedDelivery(LocalDate date, LocalTime time) {
		this.EstimatedDate = date;
		this.EstimatedTime = time;
	}

}
