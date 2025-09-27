package com.springbootapp.model;

public class PaymentRequest {
    private String ammount;
    private String paymentMethod;

    public PaymentRequest() {
    }

    public PaymentRequest(String ammount, String paymentMethod) {
        this.ammount = ammount;
        this.paymentMethod = paymentMethod;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}