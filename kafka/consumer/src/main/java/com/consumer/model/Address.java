package com.consumer.model;

public class Address {
    private String homeNumber;
    private Integer pincode;


    public Address() {    }

    public Address(String homeNumber, Integer pincode) {
        this.homeNumber = homeNumber;
        this.pincode = pincode;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }
}