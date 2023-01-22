package utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import DBHandler.DBController;
import Entities.Store;

/**
 * This class test the creation of stock status report
 * using database connection.
 */
public class ReportGeneratorTest {
	ReportGenerator reportGenerator = new ReportGenerator();
	private Store store;
	private static int month = LocalDate.now().getMonth().getValue(), year = LocalDate.now().getYear();

	/**
	 * 1. connect to DB from a given properties.
	 * 2. clear/clean all database stock report of this month/year
	 * @throws SQLException
	 */
	@BeforeAll
	static void setUp() throws SQLException {

		DBController.setDB_prop("localhost", "ekrut", "root", "Aa123456");
		ResultSet rs = DBController.select("SELECT name FROM store");
		while (rs.next()) {
			String s = String.format("DELETE FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'", month,
					year, rs.getString(1));
			DBController.update(s.toString());
		}
	}

	/**
	 * remove after every test case the store stock status report that created.
	 * @throws SQLException on database connection error.
	 */
	@AfterEach
	void tearDown() throws SQLException {
		if (store != null) {
			String s = String.format("DELETE FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'",
					reportGenerator.getMonth(), reportGenerator.getYear(), store.getName());
			DBController.update(s.toString());
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = now(), year = now(), store = valid.
	// expected result: true.
	// actual result = equals: storeExcpectedQuantity = reportResultQuantity.
	@Test
	void existingStoreReportCrationSucsess() {
		store = new Store(2, 1, "Karmiel", "Kineret 33");
		reportGenerator.setMonth(month);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
		reportGenerator.generateStockStatusReport(store);
		assertTrue(reportGenerator.stockReportExist(month, year, store.getName()));
		try {
			HashMap<String, Integer> map = buildMap(store);
			ResultSet rs = getProducts(store);
			while (rs.next()) {
				String productName = rs.getString(1);
				int storeExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(storeExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database failed.
	// input data: month = valid, year = valid, store = non valid.
	// expected result: false.
	// actual result = false: stockReportExist().
	@Test
	void nonExistingStoreReportCrationFail() {
		store = new Store(2, 1, "kiryat ata", "Kineret 233");
		reportGenerator.setMonth(month);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
		reportGenerator.generateStockStatusReport(store);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
	}

	// checking functionality: report creation in database success.
	// input data: month = null, year = now(), store = valid.
	// expected result: true.
	// actual result = equals: storeExcpectedQuantity = reportResultQuantity.
	@Test
	void monthNullReportCreationSucsess() {
		store = new Store(2, 1, "Karmiel", "Kineret 33");
		reportGenerator.setMonth(null);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
		reportGenerator.generateStockStatusReport(store);
		assertTrue(reportGenerator.stockReportExist(month, year, store.getName()));
		try {
			HashMap<String, Integer> map = buildMap(store);
			ResultSet rs = getProducts(store);
			while (rs.next()) {
				String productName = rs.getString(1);
				int storeExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(storeExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = -1, year = now(), store = valid.
	// expected result: true.
	// actual result = equals: storeExcpectedQuantity = reportResultQuantity.
	@Test
	void monthNegativeReportCreationSuccsess() {
		store = new Store(2, 1, "Karmiel", "Kineret 33");
		reportGenerator.setMonth(-1);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
		reportGenerator.generateStockStatusReport(store);
		assertTrue(reportGenerator.stockReportExist(month, year, store.getName()));
		try {
			HashMap<String, Integer> map = buildMap(store);
			ResultSet rs = getProducts(store);
			while (rs.next()) {
				String productName = rs.getString(1);
				int storeExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(storeExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = 13, year = now(), store = valid.
	// expected result: true.
	// actual result = equals: storeExcpectedQuantity = reportResultQuantity.
	@Test
	void monthBiggerThan12ReportCreationSuccsess() {
		store = new Store(2, 1, "Karmiel", "Kineret 33");
		reportGenerator.setMonth(13);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
		reportGenerator.generateStockStatusReport(store);
		assertTrue(reportGenerator.stockReportExist(month, year, store.getName()));
		try {
			HashMap<String, Integer> map = buildMap(store);
			ResultSet rs = getProducts(store);
			while (rs.next()) {
				String productName = rs.getString(1);
				int storeExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(storeExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = now() , year = null, store = valid.
	// expected result: true.
	// actual result = equals: storeExcpectedQuantity = reportResultQuantity.
	@Test
	void yearNullReportCreationSuccsess() {
		store = new Store(2, 1, "Karmiel", "Kineret 33");
		reportGenerator.setMonth(month);
		reportGenerator.setYear(null);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
		reportGenerator.generateStockStatusReport(store);
		assertTrue(reportGenerator.stockReportExist(month, year, store.getName()));
		try {
			HashMap<String, Integer> map = buildMap(store);
			ResultSet rs = getProducts(store);
			while (rs.next()) {
				String productName = rs.getString(1);
				int storeExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(storeExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = null, year = null, store = valid.
	// expected result: true.
	// actual result = equals: storeExcpectedQuantity = reportResultQuantity.
	@Test
	void yearAndMonthNullReportCreationSuccsess() {
		store = new Store(2, 1, "Karmiel", "Kineret 33");
		reportGenerator.setMonth(null);
		reportGenerator.setYear(null);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
		reportGenerator.generateStockStatusReport(store);
		assertTrue(reportGenerator.stockReportExist(month, year, store.getName()));
		try {
			HashMap<String, Integer> map = buildMap(store);
			ResultSet rs = getProducts(store);
			while (rs.next()) {
				String productName = rs.getString(1);
				int storeExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(storeExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * @param store the store of the report.
	 * @return map productID : quantity in store
	 * @throws SQLException on database connection error.
	 */
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

	/**
	 * @param store the store of the report
	 * @return resultset containing stock_data
	 */
	private ResultSet getProducts(Store store) {
		String query = String.format(
				"SELECT product.pname,store_product.quantity FROM store JOIN store_product ON store.sid = store_product.sid"
						+ " JOIN product ON product.pid = store_product.pid WHERE store.sid = %s",
				store.getSid());
		return DBController.select(query);
	}

}
