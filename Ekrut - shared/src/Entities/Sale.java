package Entities;

public class Sale {
	private int saleId;
	private String name;
	private int discount;
	
	public Sale(int saleId, String name, int discount) {
		this.saleId = saleId;
		this.name = name;
		this.discount = discount;
	}
	
	public int getSaleId() {
		return saleId;
	}
	
	public String getName() {
		return name;
	}

	public int getDiscount() {
		return discount;
	}
	
}
