package Entities;

import java.io.Serializable;

public class Store implements Serializable  {
    private int sid, rid;
    private String name, address;

    public Store(int sid, int rid, String name, String address) {
        this.sid = sid;
        this.rid = rid;
        this.name = name;
        this.address = address;
    }

    public Store() {
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
