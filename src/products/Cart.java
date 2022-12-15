package products;

import java.util.ArrayList;

public class Cart {
	public static class BProduct extends Product {

		public BProduct(Product prod) {
			super(prod);
			this.quant = 0;
		}

		@Override
		public boolean consume() {
			this.quant += 1;
			return true;
		}

		@Override
		public boolean cancelItem() {
			if (quant <= 0)
				return false;
			this.quant -=1;
			return true;
		}
	}

	private ArrayList<BProduct> products;

	public Cart(ArrayList<BProduct> products) {
		this.products = products;
	}

	public BProduct findProduct(int id) {
		for (BProduct p : products) {
			if (p.getId() == id)
				return p;
		}
		return null;
	}

	public void consume(int id) {
		findProduct(id).consume();
	}

	public float totalSum() {
		int sum = 0;
		for (BProduct p : products)
			sum += p.getQuant() * p.getPrice();
		return sum;
	}

	public ArrayList<BProduct> getProducts() {
		return products;
	}

}
