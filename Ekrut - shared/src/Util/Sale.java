package Util;

public class Sale {
	public static int saleIdGenerator = 1;
	private int saleId;
	private String name;
	private int discount;
	
	public Sale(String name, int discount) {
		this.saleId = saleIdGenerator;
		saleIdGenerator++;
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
	
	public static int generateSaleId() {
		return saleIdGenerator++;
	}
	
}
