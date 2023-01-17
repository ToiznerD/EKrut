package Entities;

/**
 * CustomerReport represent a customer report
 */
public class CustomerReport {
    private String sname, histogram;
    private int year, month, minNumOrders, maxNumOrders;

    /**
     * @param sname CustomerReport's name
     * @param year CustomerReport's year
     * @param month CustomerReport's month
     * @param histogram CustomerReport's histogram
     * @param minNumOrders CustomerReport's minNumOrders
     * @param maxNumOrders CustomerReport's maxNumOrders
     */
    public CustomerReport(String sname, int year, int month, String histogram, int minNumOrders, int maxNumOrders) {
        this.sname = sname;
        this.histogram = histogram;
        this.year = year;
        this.month = month;
        this.minNumOrders = minNumOrders;
        this.maxNumOrders = maxNumOrders;
    }

    /**
     *
     * @return
     */
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    /**
     * histogram's attribute getter
     * @return histogram's attribute value
     */
    public String getHistogram() {
        return histogram;
    }

    /**
     * histogram's setter
     * @param histogram histogram value to set
     */
    public void setHistogram(String histogram) {
        this.histogram = histogram;
    }

    /**
     * year's getter
     * @return year's attribute value
     */
    public int getYear() {
        return year;
    }

    /**
     * year's setter
     * @param year year value to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * month's attribute getter
     * @return month's attribute value
     */
    public int getMonth() {
        return month;
    }

    /**
     * month's attribute setter
     * @param month month value to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * minNumOrders's attribute getter
     * @return
     */
    public int getMinNumOrders() {
        return minNumOrders;
    }

    /**
     * minNumOrders's attribute setter
     * @param minNumOrders minNumOrders value to set
     */
    public void setMinNumOrders(int minNumOrders) {
        this.minNumOrders = minNumOrders;
    }

    /**
     * maxNumOrders's attribute getter
     * @return maxNumOrders attribute value
     */
    public int getMaxNumOrders() {
        return maxNumOrders;
    }

    /**
     * maxNumOrders's attribute setter
     * @param maxNumOrders minNumOrders value to set
     */
    public void setMaxNumOrders(int maxNumOrders) {
        this.maxNumOrders = maxNumOrders;
    }
}
