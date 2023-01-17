package Entities;

/**
 * Product class represents a product in the system (Entity)
 * It has fields for the information of a product.
 */
public class Product {
    private int pid, price;
    private String pname;

	/**
	* Constructor for Product object.
	* @param pid the ID of the product
	* @param price the price of the product
	* @param pname the name of the product
	*/
    public Product(int pid, int price, String pname) {
        this.pid = pid;
        this.price = price;
        this.pname = pname;
    }

    /**
     * Gets the ID of the product.
     * @return int : product ID.
     */
    public int getPid() {
        return pid;
    }

    /**
     * Sets the the ID of the product.
     * @param int product ID
     */
    public void setPid(int pid) {
        this.pid = pid;
    }

    /**
     * Gets the price of the product.
     * @return int : product price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the the price of the product.
     * @param int product price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the name of the product.
     * @return String : product name.
     */
    public String getPname() {
        return pname;
    }

    /**
     * Sets the the name of the product.
     * @param String product name
     */
    public void setPname(String pname) {
        this.pname = pname;
    }
}
