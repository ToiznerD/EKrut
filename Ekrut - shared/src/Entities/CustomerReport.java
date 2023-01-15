package Entities;

public class CustomerReport {
    private String sname, histogram;
    private int year, month, minNumOrders, maxNumOrders;


    public CustomerReport(String sname, int year, int month, String histogram, int minNumOrders, int maxNumOrders) {
        this.sname = sname;
        this.histogram = histogram;
        this.year = year;
        this.month = month;
        this.minNumOrders = minNumOrders;
        this.maxNumOrders = maxNumOrders;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getHistogram() {
        return histogram;
    }

    public void setHistogram(String histogram) {
        this.histogram = histogram;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMinNumOrders() {
        return minNumOrders;
    }

    public void setMinNumOrders(int minNumOrders) {
        this.minNumOrders = minNumOrders;
    }

    public int getMaxNumOrders() {
        return maxNumOrders;
    }

    public void setMaxNumOrders(int maxNumOrders) {
        this.maxNumOrders = maxNumOrders;
    }
}
