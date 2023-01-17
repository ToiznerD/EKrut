package Entities;

/**
 * StockReport class represents a stock report of a store (Entity)
 * It has fields for the information stock report of a store.
 */
public class StockReport {
    private int month, year;
    private String sid, stockData;

	/**
	* Constructor for StockReport object.
	* @param sid the ID of the store
	* @param stockData the data of stock
	* @param month of the report
	* @param year of the report
	*/
    public StockReport(String sid, String stockData, int month, int year) {
        this.sid = sid;
        this.month = month;
        this.year = year;
        this.stockData = stockData;
    }

    /**
     * Gets the ID of the store.
     * @return String : store ID.
     */
    public String getSid() {
        return sid;
    }

    /**
     * Sets the the ID of the store.
     * @param String store ID
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * Gets the month of the report.
     * @return int : report month.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the the month of the report.
     * @param int report month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Gets the year of the report.
     * @return int : report year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the the year of the report.
     * @param int report year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the data of the stock.
     * @return String : stock data
     */
    public String getStockData() {
        return stockData;
    }

    /**
     * Sets the stock data.
     * @param String stock data
     */
    public void setStockData(String stockData) {
        this.stockData = stockData;
    }
}
