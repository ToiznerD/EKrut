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
	private static Store validStore,nonValidStore;
	private static int month = LocalDate.now().getMonth().getValue(), year = LocalDate.now().getYear();

	/**
	 * 1. connect to DB from a given properties.
	 * 2. clear/clean all database stock report of this month/year
	 * @throws SQLException
	 */
	@BeforeAll
	static void setUp() throws SQLException {

		DBController.setDB_prop("localhost", "ekrut", "root", "Aa123456");
		ResultSet rs = DBController.select("SELECT name FROM validStore");
		while (rs.next()) {
			String s = String.format("DELETE FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'", month,
					year, rs.getString(1));
			DBController.update(s.toString());
		}
		validStore = new Store(2, 1, "Karmiel", "Kineret 33");
		nonValidStore = new Store(2,1,"Kiryat ata","Kineret 33");

	}

	/**
	 * remove after every test case the validStore stock status report that created.
	 * @throws SQLException on database connection error.
	 */
	@AfterEach
	void tearDown() throws SQLException {
		if (validStore != null) {
			String s = String.format("DELETE FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'",
					reportGenerator.getMonth(), reportGenerator.getYear(), validStore.getName());
			DBController.update(s.toString());
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = now(), year = now(), validStore = (id=2,rid=1,name=karmiel,address=kineret 33).
	// expected result: equals: validStoreExcpectedQuantity = reportResultQuantity.
	@Test
	void existingStoreReportCrationSucsess_generateStockStatusReport() {
		validStore = new Store(2, 1, "Karmiel", "Kineret 33");
		reportGenerator.setMonth(month);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, validStore.getName()));
		reportGenerator.generateStockStatusReport(validStore);
		assertTrue(reportGenerator.stockReportExist(month, year, validStore.getName()));
		try {
			HashMap<String, Integer> map = buildMap(validStore);
			ResultSet rs = getProducts(validStore);
			while (rs.next()) {
				String productName = rs.getString(1);
				int validStoreExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(validStoreExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database failed.
	// input data: month = now(), year = now(), nonValidStore = (id=2,rid=1,name=kiryat ata,address=kineret 33).
	// expected result: false: stockReportExist().
	@Test
	void nonExistingStoreReportCrationFail_generateStockStatusReport() {
		reportGenerator.setMonth(month);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, nonValidStore.getName()));
		reportGenerator.generateStockStatusReport(validStore);
		assertFalse(reportGenerator.stockReportExist(month, year, nonValidStore.getName()));
	}

	// checking functionality: report creation in database success.
	// input data: month = now(), year = now(), validStore = (id=2,rid=1,name=karmiel,address=kineret 33).
	// expected result: equals: validStoreExcpectedQuantity = reportResultQuantity.
	@Test
	void monthNullReportCreationSucsess_generateStockStatusReport() {

		reportGenerator.setMonth(null);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, validStore.getName()));
		reportGenerator.generateStockStatusReport(validStore);
		assertTrue(reportGenerator.stockReportExist(month, year, validStore.getName()));
		try {
			HashMap<String, Integer> map = buildMap(validStore);
			ResultSet rs = getProducts(validStore);
			while (rs.next()) {
				String productName = rs.getString(1);
				int validStoreExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(validStoreExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = now(), year = now(), validStore = (id=2,rid=1,name=karmiel,address=kineret 33).
	// expected result: equals: validStoreExcpectedQuantity = reportResultQuantity.
	@Test
	void monthNegativeReportCreationSuccsess_generateStockStatusReport() {
		reportGenerator.setMonth(-1);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, validStore.getName()));
		reportGenerator.generateStockStatusReport(validStore);
		assertTrue(reportGenerator.stockReportExist(month, year, validStore.getName()));
		try {
			HashMap<String, Integer> map = buildMap(validStore);
			ResultSet rs = getProducts(validStore);
			while (rs.next()) {
				String productName = rs.getString(1);
				int validStoreExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(validStoreExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = now(), year = now(), validStore = (id=2,rid=1,name=karmiel,address=kineret 33).
	// expected result: equals: validStoreExcpectedQuantity = reportResultQuantity.
	@Test
	void monthBiggerThan12ReportCreationSuccsess_generateStockStatusReport() {
		validStore = new Store(2, 1, "Karmiel", "Kineret 33");
		reportGenerator.setMonth(13);
		reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, validStore.getName()));
		reportGenerator.generateStockStatusReport(validStore);
		assertTrue(reportGenerator.stockReportExist(month, year, validStore.getName()));
		try {
			HashMap<String, Integer> map = buildMap(validStore);
			ResultSet rs = getProducts(validStore);
			while (rs.next()) {
				String productName = rs.getString(1);
				int validStoreExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(validStoreExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = now(), year = now(), validStore = (id=2,rid=1,name=karmiel,address=kineret 33).
	// expected result: equals: validStoreExcpectedQuantity = reportResultQuantity.
	@Test
	void yearNullReportCreationSuccsess_generateStockStatusReport() {
		reportGenerator.setMonth(month);
		reportGenerator.setYear(null);
		assertFalse(reportGenerator.stockReportExist(month, year, validStore.getName()));
		reportGenerator.generateStockStatusReport(validStore);
		assertTrue(reportGenerator.stockReportExist(month, year, validStore.getName()));
		try {
			HashMap<String, Integer> map = buildMap(validStore);
			ResultSet rs = getProducts(validStore);
			while (rs.next()) {
				String productName = rs.getString(1);
				int validStoreExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(validStoreExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	// checking functionality: report creation in database success.
	// input data: month = now(), year = now(), validStore = (id=2,rid=1,name=karmiel,address=kineret 33).
	// expected result: equals: validStoreExcpectedQuantity = reportResultQuantity.
	@Test
	void yearAndMonthNullReportCreationSuccsess_generateStockStatusReport() {
		reportGenerator.setMonth(null);
		reportGenerator.setYear(null);
		assertFalse(reportGenerator.stockReportExist(month, year, validStore.getName()));
		reportGenerator.generateStockStatusReport(validStore);
		assertTrue(reportGenerator.stockReportExist(month, year, validStore.getName()));
		try {
			HashMap<String, Integer> map = buildMap(validStore);
			ResultSet rs = getProducts(validStore);
			while (rs.next()) {
				String productName = rs.getString(1);
				int validStoreExcpectedQuantity = rs.getInt(2);
				int reportResultQuantity = map.get(productName);
				assertEquals(validStoreExcpectedQuantity, reportResultQuantity);
			}
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * @param validStore the validStore of the report.
	 * @return map productID : quantity in validStore
	 * @throws SQLException on database connection error.
	 */
	private HashMap<String, Integer> buildMap(Store validStore) throws SQLException {
		String stockQuery = String.format(
				"SELECT stock_data FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'",
				reportGenerator.getMonth(), reportGenerator.getYear(), validStore.getName());
		ResultSet rs = DBController.select(stockQuery);
		rs.first();
		String[] stockData = rs.getString(1).split(",");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < stockData.length - 1; i += 2)
			map.put(stockData[i], Integer.parseInt(stockData[i + 1]));
		return map;
	}

	/**
	 * @param validStore the validStore of the report
	 * @return resultset containing stock_data
	 */
	private ResultSet getProducts(Store validStore) {
		String query = String.format(
				"SELECT product.pname,validStore_product.quantity FROM validStore JOIN validStore_product ON validStore.sid = validStore_product.sid"
						+ " JOIN product ON product.pid = validStore_product.pid WHERE validStore.sid = %s",
				validStore.getSid());
		return DBController.select(query);
	}

}
