package com.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic myNewTopic(){
        return new NewTopic("topic-a",3,(short)1);

//        return TopicBuilder
//                .name("topic-c")
//                .partitions(3)
//                .replicas(1)
//                .build();
    }

}
