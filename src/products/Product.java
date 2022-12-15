package products;

import java.util.Objects;

public abstract class Product {

	private int id;
	protected int quant;
	private String name;
	private double price;

	public Product(int id, String name, int quant, double price) {
		this.id = id;
		this.quant = quant;
		this.name = name;
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, quant);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return id == other.id && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price) && quant == other.quant;
	}

	public Product(Product prod) {
		this.id = prod.getId();
		this.quant = prod.getQuant();
		this.name = prod.getName();
		this.price = prod.getPrice();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuant() {
		return quant;
	}

	public boolean setQuant(int quant) {
		if (quant < 0)
			return false;
		this.quant = quant;
		return true;
	}

	public abstract boolean cancelItem();
	public abstract boolean consume();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
