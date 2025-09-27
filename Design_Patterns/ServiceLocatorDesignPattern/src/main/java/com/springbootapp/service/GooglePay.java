package com.springbootapp.service;

import org.springframework.stereotype.Service;

import com.springbootapp.model.PaymentRequest;
import com.springbootapp.repo.PaymentService;

@Service("GooglePay")
public class GooglePay implements PaymentService {
    @Override
    public String pay(PaymentRequest request) {
        System.out.println("GPay Called");
        return "G_Pay Done";
    }
}
