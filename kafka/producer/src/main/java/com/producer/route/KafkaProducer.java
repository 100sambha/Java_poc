package com.producer.route;

import com.producer.model.Address;
import com.producer.model.User;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
public class KafkaProducer {

    final KafkaTemplate<String, User> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


//    @PostMapping("/create")
//    public String createEvent(@RequestParam String message) {
//        Address address = new Address("42", 800976);
//        User user = new User(123, "Sambhaji", address);
//        this.kafkaTemplate.send("my-new-topic",user);
//        return "Message Sent\n"+user.getUserId();
//    }
}