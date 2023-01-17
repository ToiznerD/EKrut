package Entities;

/**
 * Region class represents a region in the system (Entity)
 * It has fields for the information of a region.
 */
public class Region {
	private int rid;
	private String name;

	/**
	* Constructor for Region object.
	* @param rid the ID of the region
	* @param name the name of the region
	*/
	public Region(int rid, String name) {
		this.rid = rid;
		this.name = name;
	}
	
    /**
     * Gets the ID of the region.
     * @return int : region ID
     */
	public int getRid() {
		return rid;
	}

    /**
     * Gets the name of the region.
     * @return String : region name
     */
	public String getName() {
		return name;
	}

}
