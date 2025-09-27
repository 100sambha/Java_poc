package com.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/get")
    public String welcome(){
        return "WELCOME Sambhaji";
    }

    @PostMapping("/test")
    public String hello(){
        return "Hello Sambhaji";
    }

}