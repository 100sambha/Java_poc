package com.springbootapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootapp.model.PaymentRequest;
import com.springbootapp.repo.PaymentRegistry;

@RestController
public class ServiceLocatorController {

    @Autowired
    PaymentRegistry registry;

    @PostMapping("/pay")
    public String payNow(@RequestBody PaymentRequest request){
        String response="";
        response = registry.getServiceBean(request.getPaymentMethod()).pay(request);
        return response;
    }
}
