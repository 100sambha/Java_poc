package com.springbootapp.repo;

public interface PaymentRegistry {
    public PaymentService getServiceBean(String serviceName);
}
