package products;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DBHandler.DBController;
import products.Basket.BProduct;
import products.ProductManager.MProduct;



public class ProductManagerTest {
	/* 
	 * TO TEST
	 * commit
	 * abort
	 */
	private ProductManager PM;
	private ProductManager.MProduct p1, p2, p3;

	@Before
	public void setUp() {
		DBController.setDB_prop("localhost", "ekrut", "root", "n1a2v3e4");
		try {
			PM = ProductManager.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p1 = new MProduct(1, "cola", 2, 5);
		p2 = new MProduct(2, "aviel", 3, 2.5);
		p3 = new MProduct(3, "erik", 0, 3);
	}

	@Test
	public void getProductsTest() {
		assertEquals(PM.getProducts().size(), 3);
	}

	@Test
	public void fetchProductFromDBSuccsess() {
		assertEquals(PM.findProduct(1), p1);
		assertEquals(PM.findProduct(2), p2);
		assertEquals(PM.findProduct(3), p3);
	}

	@Test
	public void consumeProductSuccsess() {

		assertTrue(PM.consume(1));
		assertEquals(PM.findProduct(1).getQuant(), p1.getQuant() - 1);
	}

	@Test
	public void consumeProductFail() {
		assertFalse(PM.consume(3));
		assertEquals(PM.findProduct(3).getQuant(), 0);
	}

	@Test
	public void createBasketTest() {
		Basket b = PM.createBasket();
		BProduct bp1 = new BProduct(p1);
		BProduct bp2 = new BProduct(p2);
		BProduct bp3 = new BProduct(p3);
		assertEquals(b.findProduct(1), bp1);
		assertEquals(b.findProduct(2), bp2);
		assertEquals(b.findProduct(3), bp3);
	}

	@Test
	public void commitBasketSuccsess() throws SQLException {
		Basket b = PM.createBasket();
		if (PM.consume(1))
			b.consume(1);
		assertEquals(0, PM.findProduct(1).getQuant()); //after new basket consume 1.
		PM.commit(b);
		ResultSet rs = DBController.select("SELECT quant FROM products WHERE id = 1");
		rs.first();
		assertEquals(1, rs.getInt(1)); //DB ORIGINAL has 2 quant, after consume 1 from basket quant = 1.
	}

	@Test
	public void abortBasketSuccsess() {
		Basket b = PM.createBasket();
		if (PM.consume(2))
			b.consume(2);
		assertEquals(2, PM.findProduct(2).getQuant());
		PM.abort(b);
		assertEquals(3, PM.findProduct(2).getQuant());
	}

	@After
	public void tearUp() {
		String query1 = "UPDATE products SET quant = 2 WHERE id = 1";
		String query2 = "UPDATE products SET quant = 3 WHERE id = 2";
		String query3 = "UPDATE products SET quant = 0 WHERE id = 3";
		DBController.update(query1);
		DBController.update(query2);
		DBController.update(query3);
	}
}
