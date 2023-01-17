package Entities;

/**
 * ResupplyRequest class represents a resupply request of store product inventory
 */
public class ResupplyRequest {
    String sname,pname,status;
    int uid, quantity;

    /**
     * @param sname ResupplyRequest's sname
     * @param pname ResupplyRequest's pname
     * @param uid ResupplyRequest's uid
     * @param quantity ResupplyRequest's quantity
     * @param status ResupplyRequest's status
     */
    public ResupplyRequest(String sname, String pname, int uid, int quantity, String status) {
        this.sname = sname;
        this.pname = pname;
        this.uid = uid;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * @param sname ResupplyRequest's sname
     * @param pname ResupplyRequest's pname
     * @param uid ResupplyRequest's uid
     * @param quantity ResupplyRequest's quantity
     */
    public ResupplyRequest(String sname, String pname, int uid, int quantity) {
        this.sname = sname;
        this.pname = pname;
        this.uid = uid;
        this.quantity = quantity;
    }

    /**
     * sname getter
     * @return sname attribute value
     */
    public String getSname() {
        return sname;
    }

    /**
     * sname's setter
     * @param sname sname value to set
     */
    public void setSname(String sname) {
        this.sname = sname;
    }

    /**
     * pname's getter
     * @return pname attribute value
     */
    public String getPname() {
        return pname;
    }

    /**
     * pname's setter
     * @param pname pname value to set
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * status's getter
     * @return status attribute value
     */
    public String getStatus() {
        return status;
    }

    /**
     * status's setter
     * @param status sttatus value to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * uid's getter
     * @return uid attribute value
     */
    public int getUid() {
        return uid;
    }

    /**
     * uid's setter
     * @param uid uid value to set
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * quantity's getter
     * @return quantity attribute value
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * quantity setter
     * @param quantity quantity value to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
