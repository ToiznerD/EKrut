package Entities;

import java.io.Serializable;
import java.util.Objects;


public class ResupplyProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sid, pid, rquant, aquant;
	private String sname, pname, status;

	//store id,Store name,product id,product name,store_product quantity,resupply_request quantity,status
	public ResupplyProduct(int sid, String sname, int pid, String pname, int aquant, int rquant, String status) {
		this.sid = sid;
		this.sname = sname;
		this.pid = pid;
		this.pname = pname;
		this.rquant = rquant;
		this.aquant = aquant;
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pid + sid);
	}

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

	public int getSid() {
		return sid;
	}

	public int getPid() {
		return pid;
	}

	public int getRquant() {
		return rquant;
	}

	public int getAquant() {
		return aquant;
	}

	public String getSname() {
		return sname;
	}

	public String getPname() {
		return pname;
	}

	public String getStatus() {
		return status;
	}

}
