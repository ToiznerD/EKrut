package Util;

public class SaleTemplate {
	private int id, discount;
	private String name;
	
	public SaleTemplate(int id, String name, int discount) {
		this.id = id;
		this.name = name;
		this.discount = discount;
	}
	
	public int getTemplateId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public int getDiscount() {
		return discount;
	}
	
}
