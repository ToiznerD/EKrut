package DBHandler;

import java.io.Serializable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer implements Serializable {
	private SimpleStringProperty fName, lName, phoneNum, email, creditNum;
	private SimpleIntegerProperty id, subNum;
	
	public Customer(String fName, String lName, int id, String phoneNum, String email, String creditNum, int subNum) {
		this.fName = new SimpleStringProperty(fName);
		this.lName = new SimpleStringProperty(lName);
		this.phoneNum = new SimpleStringProperty(phoneNum);
		this.email = new SimpleStringProperty(email);
		this.creditNum = new SimpleStringProperty(creditNum);
		this.id = new SimpleIntegerProperty(id);
		this.subNum = new SimpleIntegerProperty(subNum);
	}
	
	public Customer(int id, String creditNum, int subNum) {
		this.id = new SimpleIntegerProperty(id);
		this.creditNum = new SimpleStringProperty(creditNum);
		this.subNum = new SimpleIntegerProperty(subNum);
	}
	
	
	public String getFName() {
		return fName.get();
	}
	public String getLName() {
		return lName.get();
	}
	public String getPhoneNum() {
		return phoneNum.get();
	}
	public String getEmail() {
		return email.get();
	}
	public String getCreditNum() {
		return creditNum.get();
	}
	public int getId() {
		return id.get();
	}
	public int getSubNum() {
		return subNum.get();
	}
	
	
	
	public static Customer CreateCustomerFromArrData(ArrayList<Object> dataArr) throws SQLException {
		return new Customer((String)dataArr.get(0),
							(String)dataArr.get(1), 
				            (int)dataArr.get(2), 
				            (String)dataArr.get(3), 
				            (String)dataArr.get(4), 
				            (String)dataArr.get(5), 
				            (int)dataArr.get(6)			            
							);
	}

	public static ArrayList<Customer> CreateCustomer2DArr(ArrayList<ArrayList<Object>> TwoDArrArr) {
		ArrayList<Customer> CustomerArr = new ArrayList<Customer>();
		
		try {
	    	for (ArrayList<Object> innerArr : TwoDArrArr) {
	    		CustomerArr.add(Customer.CreateCustomerFromArrData(innerArr));
	    	}
		} catch (Exception e) {
              e.printStackTrace();
		}	
		return CustomerArr;
		
	}
	
}
