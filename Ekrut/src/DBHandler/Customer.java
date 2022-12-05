package DBHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7273025852605557097L;
	private transient StringProperty fName, lName, phoneNum, email, creditNum;
	private transient SimpleIntegerProperty id, subNum;
	
	public Customer(String fName, String lName, int id, String phoneNum, String email, String creditNum, int subNum) {
		this.fName = new SimpleStringProperty(fName);
		this.lName = new SimpleStringProperty(lName);
		this.phoneNum = new SimpleStringProperty(phoneNum);
		this.email = new SimpleStringProperty(email);
		this.creditNum = new SimpleStringProperty(creditNum);
		this.id = new SimpleIntegerProperty(id);
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


	//Functions to make StringXXXProperty Serializable
	//writeObject defines how to send the properties
	//readObject defines how to read the properties
	private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(fName.get());
		s.writeUTF(lName.get());
		s.writeUTF(phoneNum.get());
		s.writeUTF(email.get());
		s.writeUTF(creditNum.get());
		s.writeInt(getId());
		s.writeInt(getSubNum());
    }


    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    	fName = new SimpleStringProperty(s.readUTF());
    	lName = new SimpleStringProperty(s.readUTF());
    	phoneNum = new SimpleStringProperty(s.readUTF());
    	email = new SimpleStringProperty(s.readUTF());
    	creditNum = new SimpleStringProperty(s.readUTF());
    	id = new SimpleIntegerProperty(s.readInt());
    	subNum = new SimpleIntegerProperty(s.readInt());
    }
    
    public static ArrayList<Customer> createCustomerArray(ResultSet rs) throws SQLException {
		ArrayList<Customer> customerArray = new ArrayList<>();
		while(rs.next()) {
			customerArray.add(new Customer(rs.getString(1), rs.getString(2),
											rs.getInt(3), rs.getString(4),
											rs.getString(5), rs.getString(6),
											rs.getInt(7)));
		}
		return customerArray;
	
	}
    
	@Override
	public String toString() {
		return "[fName=" + fName + ", lName=" + lName + ", phoneNum=" + phoneNum + ", email=" + email
				+ ", creditNum=" + creditNum + ", id=" + id + ", subNum=" + subNum + "]";
	}
	
	
}
