package controllers;

import org.junit.jupiter.api.BeforeEach;
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
			
				if (reportInDb.getMonth() != intMonth || reportInDb.getYear() != intYear || !reportInDb.getSname().equals(storeLocation))
					stockReportController.setStockReportToDisplay(null);
			} catch (NumberFormatException e) {
				stockReportController.setStockReportToDisplay(null);
			}
			
			stockReportController.setStockReportToDisplay(reportInDb);
		}
	
	}
	
	@BeforeEach
	void setUp() {
	}
	
	
	/**
	 * test cases : reportExist, reportDontExist, storeLocation = "", month = "", year = "", month = year = storeLocation = ""
	 */
	
	
	
}
