package Entities;

import java.io.Serializable;

/**
 * OrderReport class represents a report of orders according to a store(Entity)
 * It has fields for the information of that report.
 */
public class OrderReport implements Serializable {

	private static final long serialVersionUID = 1L;
	private int numOrders, totalProfit, month, year;
    private String sName;

	/**
	* Constructor for OrderReport object.
	* @param sName the name of the store
	* @param numOrders the number of orders
	* @param totalProfit the total profit of all orders in the report
	*/
    public OrderReport(String sName, int numOrders, int totalProfit) {
        this.numOrders = numOrders;
        this.totalProfit = totalProfit;
        this.sName = sName;
    }

    /**
     * Gets the number of orders of a store
     * @return number of orders
     */
    public int getNumOrders() {
        return numOrders;
    }

    /**
     * Sets thenumber of orders in the report
     * @param numOrders number of orders
     */
    public void setNumOrders(int numOrders) {
        this.numOrders = numOrders;
    }

    /**
     * Gets the total profit of all orders in the report
     * @return total profit
     */
    public int getTotalProfit() {
        return totalProfit;
    }

    /**
     * Sets the total profit of all orders in the report
     * @param totalProfit the total profit of a store
     */
    public void setTotalProfit(int totalProfit) {
        this.totalProfit = totalProfit;
    }

    /**
     * Gets the name of the store
     * @return store name
     */
    public String getsName() {
        return sName;
    }

    /**
     * Sets the name of the store
     * @param sName name of the store
     */
    public void setsName(String sName) {
        this.sName = sName;
    }

    /**
     * Gets the month of the report.
     * @return report month.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month of the report.
     * @param month the number of the month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Gets the year of the report.
     * @return report year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the the year of the report.
     * @param year the year in numbers
     */
    public void setYear(int year) {
        this.year = year;
    }
}
