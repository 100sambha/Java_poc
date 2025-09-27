package com.consumer.listener;

import com.consumer.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumerListenerFunctionStreams {

    @Bean
    public Consumer<User> processUser(){
        return user->{
            System.out.println(user.getUserId()+" "+user.getName()+" "+user.getAddress().getHomeNumber()+" "+user.getAddress().getPincode());
        };
    }


    @Bean
    public Consumer<String> processUserStatus(){
        return status->{
            System.out.println("User Status "+status);
        };
    }
}