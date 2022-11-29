package DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
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
	
	
	public String getfName() {
		return fName;
	}
	public String getlName() {
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
	
	public static Customer CreateCustomerFromRS(ResultSet rs) throws SQLException {
		return new Customer(rs.getString(1), 
							rs.getString(2), 
							rs.getInt(3), 
							rs.getString(4), 
							rs.getString(5), 
							rs.getString(6), 
							rs.getInt(7)
							);
	}
	
	
}
