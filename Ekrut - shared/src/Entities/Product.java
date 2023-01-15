package Entities;

public class Product {
    private int pid, price;
    private String pname;

    public Product(int pid, int price, String pname) {
        this.pid = pid;
        this.price = price;
        this.pname = pname;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
