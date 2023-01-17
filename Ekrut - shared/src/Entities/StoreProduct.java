package Entities;

/**
 * StoreProduct class represents the products in each store (Entity)
 * It has fields for the information each product in a store.
 */
public class StoreProduct {
    private int pid,sid,quantity,minLimit;
    private String pname;

    /**
     * Constructor for the StoreProduct object.
     * @param pid : product ID
     * @param sid : store ID
     * @param quantity : product quantity available in a store
     * @param minLimit : Product minimum limit that have to exist in a store
     * @param pname : product name
     */
    public StoreProduct(int pid, int sid, int quantity, int minLimit, String pname) {
        this.pid = pid;
        this.sid = sid;
        this.quantity = quantity;
        this.minLimit = minLimit;
        this.pname = pname;
    }

    /**
     * Gets the ID of a product.
     * @return product ID.
     */
    public int getPid() {
        return pid;
    }

    /**
     * Sets the product id.
     * @param pid product id.
     */
    public void setPid(int pid) {
        this.pid = pid;
    }

    /**
     * Gets the ID of a store.
     * @return store ID.
     */
    public int getSid() {
        return sid;
    }

    /**
     * Sets the store id.
     * @param sid store id.
     */
    public void setSid(int sid) {
        this.sid = sid;
    }

    /**
     * Gets the quantity of a product.
     * @return product quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of a product in a store.
     * @param quantity product quntity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the minimum limit of a product in a store.
     * @return product minimum limit number.
     */
    public int getMinLimit() {
        return minLimit;
    }

    /**
     * Sets the minimum limit of a product in a store.
     * @param minLimit product minimum limit number.
     */
    public void setMinLimit(int minLimit) {
        this.minLimit = minLimit;
    }

    /**
     * Gets the product name.
     * @return product name.
     */
    public String getPname() {
        return pname;
    }

    /**
     * Sets the product name.
     * @param pname product name
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

}
