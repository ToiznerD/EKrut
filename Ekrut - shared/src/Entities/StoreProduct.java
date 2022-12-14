package Entities;

public class StoreProduct {
    private int pid,sid,quantity,minLimit;
    private String pname;

    public StoreProduct(int pid, int sid, int quantity, int minLimit, String pname) {
        this.pid = pid;
        this.sid = sid;
        this.quantity = quantity;
        this.minLimit = minLimit;
        this.pname = pname;
    }

    public StoreProduct(int quantity, String pname) {
        this.quantity = quantity;
        this.pname = pname;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(int minLimit) {
        this.minLimit = minLimit;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

}
