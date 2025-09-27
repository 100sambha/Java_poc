package com.producer.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class UppercaseBeanAsEndPoint {

    @Bean
    public Function<String, String> uppercase() {
        return val -> val.toUpperCase();
    }
}
