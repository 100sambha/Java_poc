package com.producer.model;

public class User {
    private Integer userId;
    private String name;
    private Address address;

    public User() {
    }

    public User(Integer userId, String name, Address address) {
        this.userId = userId;
        this.name = name;
        this.address = address;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}