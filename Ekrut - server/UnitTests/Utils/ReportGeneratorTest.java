package Utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
		DBController.setDB_prop("localhost", "ekrut", "root", "Nati147258369");
		//
		// set all users to be logged off in db
		ResultSet rs = DBController.select("SELECT name FROM store");
		while (rs.next()) {
			String s = String.format("DELETE FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'", month, year, rs.getString(1));
			DBController.update(s.toString());
		}
	}
	
	@AfterEach
	void tearDown() throws Exception {
		// delete all reports that was generated in each test case
		if (store != null) {
			String s = String.format("DELETE FROM stock_report WHERE month = %d AND year = %d AND s_name = '%s'", reportGenerator.getMonth(), reportGenerator.getYear(), store.getName());
			DBController.update(s.toString());
		}
	}
	
	
	/*
	 * Test cases : StoreThatExist, StoreDontExist
	 */
	@Test
	void correctKarmielStoreGenerateStockStatusReport() {	
		store = new Store(2,1,"Karmiel","Kineret 33");
		reportGenerator.setMonth(month); reportGenerator.setYear(year);
		assertFalse(reportGenerator.stockReportExist(month, year, store.getName()));
		
		reportGenerator.generateStockStatusReport(store);
		
		assertTrue(reportGenerator.stockReportExist(month, year, store.getName()));
	}
	
	
	
}
