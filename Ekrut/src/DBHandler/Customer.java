package DBHandler;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fName, lName, phoneNum, email, creditNum;
	private int id, subNum;

	public Customer(String fName, String lName, int id, String phoneNum, String email, String creditNum, int subNum) {
		this.fName = fName;
		this.lName = lName;
		this.phoneNum = phoneNum;
		this.email = email;
		this.creditNum = creditNum;
		this.id = id;
		this.subNum = subNum;
	}

	public String getFName() {
		return fName;
	}

	public String getLName() {
		return lName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public String getCreditNum() {
		return creditNum;
	}

	public int getId() {
		return id;
	}

	public int getSubNum() {
		return subNum;
	}

	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}

	public void setSubNum(int subNum) {
		this.subNum = subNum;
	}

	public static ArrayList<Customer> createCustomerArray(ResultSet rs) throws SQLException {
		ArrayList<Customer> customerArray = new ArrayList<>();
		while (rs.next()) {
			customerArray.add(new Customer(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getInt(7)));
		}
		return customerArray;
	}

	public static ObservableList<Customer> observableCustomer(ArrayList<Customer> customerArray) {
		return FXCollections.observableArrayList(customerArray);
	}

	/*
	 * @Override public String toString() { return "[fName=" + fName + ", lName=" +
	 * lName + ", phoneNum=" + phoneNum + ", email=" + email + ", creditNum=" +
	 * creditNum + ", id=" + id + ", subNum=" + subNum + "]"; }
	 */

}
