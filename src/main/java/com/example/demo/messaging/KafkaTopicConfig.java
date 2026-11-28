package com.example.demo.messaging;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String LOCATION_UPDATES = "shipment.location-updates";
    public static final String LOCATION_UPDATES_DLT = "shipment.location-updates.DLT";

    @Bean
    public NewTopic locationUpdatesTopic(){
        return TopicBuilder.name(LOCATION_UPDATES).partitions(2).replicas(1).build();

    }

    @Bean
    public NewTopic locationUpdatesDLTTopic(){
        return TopicBuilder.name(LOCATION_UPDATES_DLT).partitions(2).replicas(1).build();
    }
}
