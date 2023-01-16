package Entities;

public class MinimalStoreProduct {
    private int quantity;
    private String pname;

    public MinimalStoreProduct(int quantity, String pname) {
        this.quantity = quantity;
        this.pname = pname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
