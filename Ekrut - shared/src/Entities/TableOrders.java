package Entities;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * TableOrders class represents a delivery order (Entity)
 * It has fields for the information of the order.
 */
public class TableOrders implements Serializable{

	private static final long serialVersionUID = 1L;
	private int OrderID, CustomerID;
	private String RecieverName, RecieverAddress, RecieverPhone, Status;
	private LocalDate OrderDate, EstimatedDate;
	private LocalTime OrderTime, EstimatedTime;
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");
	private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

	/**
	* Constructor for TableOrders object.
	* @param CustomerID the ID of the customer who placed the order
	* @param OrderID the ID of the order
	* @param RecieverName the name of the customer
	* @param RecieverAddress the destination address specified in the order
	* @param RecieverPhone the phone number of the customer
	* @param OrderDate the date the order was placed
	* @param OrderTime the time the order was placed
	* @param Status the status of the order
	* @param EstimatedDate the estimated date of delivery
	* @param EstimatedTime the estimated time of delivery
	*/
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

	/**
	* Converts a sql.date to LocalDate.
	* It adds 1 day (because mysql-connector-java-8.0.13.jar wrong date retrieval)
	* @param date the date to convert
	* @return the converted LocalDate
	*/
	public LocalDate convertdate(Date date) {
		if (date == null)
			return null;
		return new Timestamp(date.getTime()).toLocalDateTime().toLocalDate().plusDays(1);
	}

	/**
	* Converts a sql.time to LocalTime.
	* It adds 3.5 hours (because mysql-connector-java-8.0.13.jar wrong time retrieval)
	* @param time the date to convert
	* @return the converted LocalTime
	*/
	public LocalTime converttime(Time time) {
		if (time == null)
			return null;
		return new Timestamp(time.getTime()).toLocalDateTime().toLocalTime().plusMinutes(210);
	}

    /**
     * Gets the ID of the user.
     * @return User ID.
     */
	public int getCustomerID() {// erik
		return CustomerID;
	}

    /**
     * Gets the ID of the order.
     * @return order ID.
     */
	public int getOrderID() {
		return OrderID;
	}

    /**
     * Gets the name of the user.
     * @return User name.
     */
	public String getRecieverName() {
		return RecieverName;
	}

    /**
     * Gets the address of the reciever.
     * @return reciever address.
     */
	public String getRecieverAddress() {
		return RecieverAddress;
	}

    /**
     * Gets the phone of the user.
     * @return User phone.
     */
	public String getRecieverPhone() {
		return RecieverPhone;
	}

    /**
     * Gets the order date.
     * @return order date.
     */
	public LocalDate getOrderDate() {
		return OrderDate;
	}

    /**
     * Gets the order time.
     * @return order time.
     */
	public LocalTime getOrderTime() {
		return OrderTime;
	}

    /**
     * Gets the status of the order.
     * @return order status.
     */
	public String getStatus() {
		return Status;
	}

    /**
     * Gets the estimated order date.
     * @return estimated order date.
     */
	public LocalDate getEstimatedDate() {
		return EstimatedDate;
	}

    /**
     * Gets the estimated order time.
     * @return estimated order time.
     */
	public LocalTime getEstimatedTime() {
		return EstimatedTime;
	}

    /**
     * Gets the the order date and time.
     * @return order date and time
     */
	public String getOrderDateAndTime() {
		return OrderDate.format(dateFormat) + ", " + OrderTime.format(timeFormat);
	}

    /**
     * Gets the the estimated order date and time.
     * @return estimated order date and time
     */
	public String getEstimatedDelivery() {
		return EstimatedDate == null ? "Need approval"
				: EstimatedDate.format(dateFormat) + ", " + EstimatedTime.format(timeFormat);
	}

    /**
     * Sets the the estimated order date and time.
     * @param date the estimated order date
     * @param time the estimated order time
     */
	public void setEstimatedDelivery(LocalDate date, LocalTime time) {
		this.EstimatedDate = date;
		this.EstimatedTime = time;
	}

}
