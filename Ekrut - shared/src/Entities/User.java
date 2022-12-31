package Entities;

import java.io.Serializable;

public class User implements Serializable {

    private String userName, password, role, name, address, email;
    private Integer id, phone;
    private Boolean isLogged;

    public User(Integer id, String userName, String password, String role, String name,
                Integer phone, String address, String email, Boolean isLogged) {
        this.userName = userName;
        this.role = role;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.isLogged = isLogged;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String phone) {
        this.address = address;
    }

    public Boolean getLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }


}