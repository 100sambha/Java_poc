package com.producer.route;

import com.producer.model.Address;
import com.producer.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class KafkaProducerFunctionStreams {

    @Bean
    public Supplier<User> sendUser(){
        Random random = new Random();
        return ()->{
            int userId = random.nextInt(200);
            Address address = new Address("D312SP", 908426);
            User user = new User(userId, "Sambhaji", address);
            System.out.println("Sending...."+user.getUserId());
            return user;
        };
    }


    @Bean
    public Supplier<Message<String>> sendUserStatus(){
        Random random = new Random();
        return ()->{
            String status = random.nextBoolean()?"Completed":"In-Progress";
            String userId = "ID"+random.nextInt(100);
            System.out.println("sending--------"+userId);
            return MessageBuilder.withPayload(userId+" : "+status)
                    .setHeader(KafkaHeaders.KEY, userId.getBytes())
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
                    .build();
        };
    }
}