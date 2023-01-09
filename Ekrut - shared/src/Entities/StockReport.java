package Entities;

public class StockReport {
    private int month, year;
    private String sid, stockData;

    public StockReport(String sid, String stockData, int month, int year) {
        this.sid = sid;
        this.month = month;
        this.year = year;
        this.stockData = stockData;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStockData() {
        return stockData;
    }

    public void setStockData(String stockData) {
        this.stockData = stockData;
    }
}
