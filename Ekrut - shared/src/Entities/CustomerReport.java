package Entities;

public class CustomerReport {
    String s_name, histogram;
    int year, month;

    public CustomerReport(String s_name, int year, int month, String histogram) {
        this.s_name = s_name;
        this.histogram = histogram;
        this.year = year;
        this.month = month;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
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
}
