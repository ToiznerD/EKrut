package utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import DBHandler.DBController;
import Entities.Store;

public class ReportGeneratorTest {
	ReportGenerator reportGenerator = new ReportGenerator();
	private Store store;
	private static int month = 1, year = 2023;

	@BeforeAll
	static void setUp() throws Exception {
		// connect to DB
		DBController.setDB_prop("localhost", "ekrut", "root", "n1a2v3e4");
		//
		// set all users to be logged off in db
		ResultSet rs = DBController.select("SELECT name FROM store");
		while (rs.next()) {
			String s = String.format("DELETE FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'", month,
					year, rs.getString(1));
			DBController.update(s.toString());
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		// delete all reports that was generated in each test case
		if (store != null) {
			String s = String.format("DELETE FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'",
					reportGenerator.getMonth(), reportGenerator.getYear(), store.getName());
			DBController.update(s.toString());
		}
	}

	/*
	 * Test cases : StoreThatExist, StoreDontExist,month = null > 12 < 0,year=null,both=null
	 */
	@Test
	void correctKarmielStoreGenerateStockStatusReport() {
		store = new Store(2, 1, "Karmiel", "Kineret 33");
		reportGenerator.setMonth(month);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
		reportGenerator.generateStockStatusReport(store);
		assertTrue(reportGenerator.stockReportExist(month, year, store.getName()));
		try {
			HashMap<String, Integer> map = buildMap(store);
			ResultSet rs = getProducts(store);
			while (rs.next())
				assertEquals(rs.getInt(2), map.get(rs.getString(1)));
		} catch (Exception e) {
			fail();
		}
	}

	private HashMap<String, Integer> buildMap(Store store) throws SQLException {
		String stockQuery = String.format(
				"SELECT stock_data FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'",
				reportGenerator.getMonth(), reportGenerator.getYear(), store.getName());
		ResultSet rs = DBController.select(stockQuery);
		rs.first();
		String[] stockData = rs.getString(1).split(",");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < stockData.length - 1; i += 2)
			map.put(stockData[i], Integer.parseInt(stockData[i + 1]));
		return map;
	}

	private ResultSet getProducts(Store store) {
		String query = String.format(
				"SELECT product.pname,store_product.quantity FROM store JOIN store_product ON store.sid = store_product.sid"
						+ " JOIN product ON product.pid = store_product.pid WHERE store.sid = %s",
				store.getSid());
		return DBController.select(query);
	}

}
