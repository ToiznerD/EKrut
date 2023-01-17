package Entities;

import java.io.Serializable;

/**
 * Store class represents a store in system (Entity)
 * It has fields for the information of the store.
 */
public class Store implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private int sid, rid;
    private String name, address;

	/**
	* Constructor for Store object.
	* @param sid the ID of the store
	* @param rid the ID of the region the store belongs
	* @param name the name of the store
	* @param address the address of the store
	*/
    public Store(int sid, int rid, String name, String address) {
        this.sid = sid;
        this.rid = rid;
        this.name = name;
        this.address = address;
    }

    /**
     * empty Constructor
     */
    public Store() {
    }

    /**
     * Gets the ID of the store.
     * @return store ID.
     */
    public int getSid() {
        return sid;
    }

    /**
     * Sets the the ID of the store.
     * @param sid store ID
     */
    public void setSid(int sid) {
        this.sid = sid;
    }

    /**
     * Gets the ID of the region the store belongs.
     * @return region ID.
     */
    public int getRid() {
        return rid;
    }

    /**
     * Sets the the ID of the region the store belongs.
     * @param rid region ID
     */
    public void setRid(int rid) {
        this.rid = rid;
    }

    /**
     * Gets the name of the store.
     * @return store name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a store.
     * @param name store name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the store.
     * @return store address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of a store.
     * @param address store address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
