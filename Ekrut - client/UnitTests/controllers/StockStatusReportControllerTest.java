package controllers;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Entities.StockReport;


public class StockStatusReportControllerTest {
	StockReport reportInDb;
	private StockStatusReportController stockReportController = new StockStatusReportController(new ReportServiceStub());
	
	private class ReportServiceStub implements IReportService {
		// implement server-side
		@Override
		public void getReportFromDb(String storeLocation, String month, String year) {
			// month and year cant be passed as nulls
			try {
				int intMonth = Integer.parseInt(month), intYear = Integer.parseInt(year);
			
				if (reportInDb.getMonth() != intMonth || reportInDb.getYear() != intYear || !reportInDb.getSname().equals(storeLocation)) {
					return;
				}
			} catch (NumberFormatException e) {
				return;
			}
			stockReportController.setStockReportToDisplay(reportInDb);
		}
	}
	
	@BeforeEach
	void setUp() {
		stockReportController.setStockReportToDisplay(null);
		String sname = "Karmiel";
		String stockData = "Cola,15,Bamba,5,Snickers,10,Oreo,15,Bisli,0,Pringles,5,Cheetos,20,Doritos,20,White Kinder Bueno,40,Black Kinder Bueno,30";
		reportInDb = new StockReport(sname, stockData, 9, 2022);
	}
	
	/**
	 * test cases : reportExist, reportDontExist, storeLocation = "", month = "", year = "", month = year = storeLocation = ""
	 */
	
	// checking functionality: fetching a report that exist in the db
	// input data: sname = Karmiel, month = 9, year = 2022
	// expected result: true 
	@Test
	void existingReport_GetReportFromDb() {
		//Arrange
		assertEquals(stockReportController.getStockReportToDisplay(), null);
		String month = "9", year = "2022", sname = "Karmiel";
		// Act
		stockReportController.reportService.getReportFromDb(sname, month,year);	
		// Assert
		assertEquals(stockReportController.getStockReportToDisplay(), reportInDb);
	}
	
	// checking functionality: fetching a report of a store that exist, but date is not valid
	// input data: sname = Karmiel, month = 9, year = 2025
	// expected result: true, stockReportController.stockReportToDisplay is not initialized
	@Test
	void existingStoreNonExistingReportDate_GetReportFromDb() {
		//Arrange
		assertEquals(stockReportController.getStockReportToDisplay(), null);
		String month = "9", year = "2025", sname = "Karmiel";
		// Act
		stockReportController.reportService.getReportFromDb(sname, month, year);	
		// Assert
		assertEquals(stockReportController.getStockReportToDisplay(), null);
	}
	
	// checking functionality: fetching a report of a store that dont exist
	// input data: sname = Haifa, month = 9, year = 2025
	// expected result: true, stockReportController.stockReportToDisplay is not initialized
	@Test
	void nonExistingStore_GetReportFromDb() {
		//Arrange
		assertEquals(stockReportController.getStockReportToDisplay(), null);
		String month = "9", year = "2025", sname = "Haifa";
		// Act
		stockReportController.reportService.getReportFromDb(sname, month, year);	
		// Assert
		assertEquals(stockReportController.getStockReportToDisplay(), null);
	}
	
	// checking functionality: fetching a report with a null sname value
	// input data: sname = null, month = 9, year = 2022
	// expected result: true, stockReportController.stockReportToDisplay is not initialized
	@Test
	void nullStore_GetReportFromDb() {
		//Arrange
		assertEquals(stockReportController.getStockReportToDisplay(), null);
		String month = "9", year = "2022", sname = null;
		// Act
		stockReportController.reportService.getReportFromDb(sname, month, year);	
		// Assert
		assertEquals(stockReportController.getStockReportToDisplay(), null);
	}
	
	// checking functionality: fetching a report with a null month value
	// input data: sname = Karmiel, month = null, year = 2022
	// expected result: true, stockReportController.stockReportToDisplay is not initialized
	@Test
	void nullMonth_GetReportFromDb() {
		//Arrange
		assertEquals(stockReportController.getStockReportToDisplay(), null);
		String month = null, year = "2025", sname = "Karmiel";
		// Act
		stockReportController.reportService.getReportFromDb(sname, month, year);	
		// Assert
		assertEquals(stockReportController.getStockReportToDisplay(), null);
	}
	
	// checking functionality: fetching a report of a store that dont exist
	// input data: sname = Karmiel, month = 9, year = null
	// expected result: true, stockReportController.stockReportToDisplay is not initialized
	// actual result: true
	@Test
	void nullYear_GetReportFromDb() {
		//Arrange
		assertEquals(stockReportController.getStockReportToDisplay(), null);
		String month = "9", year = null, sname = "Karmiel";
		// Act
		stockReportController.reportService.getReportFromDb(sname, month, year);	
		// Assert
		assertEquals(stockReportController.getStockReportToDisplay(), null);
	}
	
	// checking functionality: fetching a report of a store that dont exist
	// input data: sname = null, month = null, year = null
	// expected result: true, stockReportController.stockReportToDisplay is not initialized
	// actual result: true
	@Test
	void allNullInputs_GetReportFromDb() {
		//Arrange
		assertEquals(stockReportController.getStockReportToDisplay(), null);
		String month = null, year = null, sname = null;
		// Act
		stockReportController.reportService.getReportFromDb(sname, month, year);	
		// Assert
		assertEquals(stockReportController.getStockReportToDisplay(), null);
	}
}
