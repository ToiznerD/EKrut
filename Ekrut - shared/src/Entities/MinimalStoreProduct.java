package Entities;

/**
 * MinimalStoreProduct class represents a basic product in a store,
 * includes a Product's name and quantity
 */
public class MinimalStoreProduct {
    private int quantity;
    private String pname;

    /**
     * @param quantity quantity of the product
     * @param pname product name
     */
    public MinimalStoreProduct(int quantity, String pname) {
        this.quantity = quantity;
        this.pname = pname;
    }

    /**
     * @return quantity attribute value
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * sets quantity attribute value
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return pname attribute value
     */
    public String getPname() {
        return pname;
    }

    /**
     * sets pname attribute value
     * @param pname
     */
    public void setPname(String pname) {
        this.pname = pname;
    }
}
