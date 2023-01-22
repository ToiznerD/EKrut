package controllers;

import Entities.StockReport;

public interface IReportService {
	 public void getReportFromDb(String storeLocation, String month, String year);
     //public void setStockReport(StockReport stockReport);
	 

}
