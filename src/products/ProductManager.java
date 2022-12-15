package products;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DBHandler.DBController;
import products.Cart.BProduct;

public class ProductManager {

	public static class MProduct extends Product {
		public MProduct(int id, String name, int quant, double price) {
			super(id, name, quant, price);
		}

		public boolean consume() {
			if (this.quant == 0)
				return false;
			this.quant -= 1;
			return true;
		}
		public boolean cancelItem() {
			this.quant +=1;
			return true;
		}
		public void cancelAll(int quant) {
			this.quant = this.quant + quant;
		}
	}

	private ArrayList<MProduct> products;
	private static ProductManager instance;

	private ProductManager() throws SQLException {
		ResultSet rs = DBController.select("SELECT * FROM products"); //fetch all product from DB.
		products = new ArrayList<>();
		while (rs.next()) {
			products.add(new MProduct(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)));
		}
	}

	private String query(Product p) {
		//return query to update DB (using for commit method)
		int quant = DBController.getQuant(p.getId()) - p.getQuant();
		return "UPDATE products SET quant = " + quant + " WHERE id = " + p.getId();
	}

	public synchronized void commit(Cart basket) {
		//update product quantity in DB (reduce from basket)
		for (BProduct p : basket.getProducts())
			if (p.getQuant() > 0)
				DBController.update(query(p));
	}

	public synchronized void abort(Cart basket) {
		//return quantity of product in basket to product manager
		for (BProduct p : basket.getProducts())
			if (p.getQuant() > 0)
				findProduct(p.getId()).cancelAll(p.getQuant());
	}

	public MProduct findProduct(int id) {
		//find product in products ArrayList
		for (MProduct p : products) {
			if (p.getId() == id)
				return p;
		}
		return null;
	}

	public synchronized boolean consume(int id) {
		//if product quantity > 0 reduce by 1 and return true
		return findProduct(id).consume();
	}

	public ArrayList<MProduct> getProducts() {
		//return products
		return products;
	}

	public Cart createBasket() {
		//return new basket for client
		ArrayList<BProduct> basketArr = new ArrayList<BProduct>();
		for (Product p : products)
			basketArr.add(new BProduct(p));
		return new Cart(basketArr);
	}

	public static ProductManager getInstance() throws SQLException {
		//return instance of ProductManager (Singleton)
		if (instance == null)
			instance = new ProductManager();
		return instance;
	}

}
