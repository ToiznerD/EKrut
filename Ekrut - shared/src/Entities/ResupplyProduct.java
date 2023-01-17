package Entities;

import java.io.Serializable;
import java.util.Objects;

/**
* The ResupplyProduct class represents a product that is being resupplied.
* It implements the Serializable interface.
*/
public class ResupplyProduct implements Serializable {

	private static final long serialVersionUID = 1L;
	private int sid, pid, rquant, aquant;
	private String sname, pname, status;

	/**
	* Constructor for ResupplyProduct object.
	* @param sid the ID of the store
	* @param sname the name of the store
	* @param pid the product ID
	* @param pname product name
	* @param aquant store product quantity
	* @param rquant resupply request quantity
	* @param status request status
	*/
	public ResupplyProduct(int sid, String sname, int pid, String pname, int aquant, int rquant, String status) {
		this.sid = sid;
		this.sname = sname;
		this.pid = pid;
		this.pname = pname;
		this.rquant = rquant;
		this.aquant = aquant;
		this.status = status;
	}

	/**
	@return the hash code value for this resupply product
	*/
	@Override
	public int hashCode() {
		return Objects.hash(pid + sid);
	}

	/**
	* @param obj the reference object to compare
	* @return true if this object is the same as the obj argument; false otherwise
	*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResupplyProduct other = (ResupplyProduct) obj;
		return pid == other.pid && sid == other.sid;
	}

	/**
	* @return the store ID
	*/
	public int getSid() {
		return sid;
	}

	/**
	* @return the product ID
	*/
	public int getPid() {
		return pid;
	}

	/**
	* @return resupply request quantity
	*/
	public int getRquant() {
		return rquant;
	}
	
	/**
	* @return the store product quantity
	*/
	public int getAquant() {
		return aquant;
	}

	/**
	* @return store name
	*/
	public String getSname() {
		return sname;
	}

	/**
	* @return product name
	*/
	public String getPname() {
		return pname;
	}

	/**
	* @return request status
	*/
	public String getStatus() {
		return status;
	}

}
