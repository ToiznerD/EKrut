package Entities;

import java.io.Serializable;
import java.util.Objects;



public  class TableProd implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String Done = "Done", Pending = "Not good";
	private int id, rQuant, aQuant;
	private String name, status;

	public TableProd(int id, String name, int rQuant, int aQuant) {
		this.id = id;
		this.name = name;
		this.rQuant = rQuant;
		this.aQuant = aQuant;
		this.status = rQuant <= aQuant ? Done : Pending;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableProd other = (TableProd) obj;
		return id == other.id;
	}

	public int getId() {
		return id;
	}

	public int getRquant() {
		return rQuant;
	}

	public int getAquant() {
		return aQuant;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public void setAquant(int quant) {
		if (quant >= 0) {
			this.aQuant = quant;
			this.status = rQuant <= aQuant ? Done : Pending;
		}
	}
}

