package Entities;

public class ResupplyRequest {
    String sname,pname,status;
    int uid, quantity;

    public ResupplyRequest(String sname, String pname, int uid, int quantity, String status) {
        this.sname = sname;
        this.pname = pname;
        this.uid = uid;
        this.quantity = quantity;
        this.status = status;
    }

    public ResupplyRequest(String sname, String pname, int uid, int quantity) {
        this.sname = sname;
        this.pname = pname;
        this.uid = uid;
        this.quantity = quantity;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
