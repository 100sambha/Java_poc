package com.springbootapp.service;

import org.springframework.stereotype.Service;

import com.springbootapp.model.PaymentRequest;
import com.springbootapp.repo.PaymentService;

@Service("PhonePay")
public class PhonePay implements PaymentService {
    @Override
    public String pay(PaymentRequest request) {
        System.out.println("PhonePay Called");
        return "Phone_Pay Done";
    }
}