package com.example.budgetmanagementshoppingsystemapplication.Model;

public class Customer {
    private String uid;
    private String cid;
    private String email;
    private String password;
    private String username;
    private String name;
    private String phone;
    private String address;

    public Customer() {
    }

    public Customer(String uid, String username, String password) {
        this.uid = uid;
        this.password = password;
        this.username = username;
    }

    public Customer(String uid, String username, String email, String name, String phone, String address) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
