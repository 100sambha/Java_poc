package com.consumer.listener;
//
//import com.consumer.model.User;
//import  org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ConsumerListener {
//
//    @KafkaListener(topics = "my-topic", groupId = "group-A")
//    public void listener1(String message){
//        System.out.println("Message Listener 1: "+message);
//    }
//
//    @KafkaListener(topics = "my-topic", groupId = "group-B")
//    public void listener2(String message){
//        System.out.println("Message Listener 2: "+message);
//    }
//
//    @KafkaListener(topics = "my-new-topic", groupId = "my-new-group")
//    public void listener3(User user){
//        System.out.println("User :- Id: "+user.getUserId()+" : "+user.getName());
////        System.out.println(user);
//    }
//
//}
