package com.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String greeet(HttpServletRequest request){
        return "Run Successfully with session Id - "+request.getSession().getId();
    }
}
