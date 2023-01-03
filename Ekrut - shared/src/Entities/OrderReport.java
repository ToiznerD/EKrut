package Entities;

import java.io.Serializable;

public class OrderReport implements Serializable {
    private int numOrders, totalProfit, month, year;
    private String sName;

    public OrderReport(String sName, int numOrders, int totalProfit) {
        this.numOrders = numOrders;
        this.totalProfit = totalProfit;
        this.sName = sName;
    }

    public int getNumOrders() {
        return numOrders;
    }

    public void setNumOrders(int numOrders) {
        this.numOrders = numOrders;
    }

    public int getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(int totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
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
}
