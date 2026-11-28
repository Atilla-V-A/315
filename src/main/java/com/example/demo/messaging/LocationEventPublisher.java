package com.example.demo.messaging;
import com.example.demo.messaging.event.LocationUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocationEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(LocationEventPublisher.class);
    private final KafkaTemplate<String, LocationUpdateEvent> kafkaTemplate;

    public LocationEventPublisher(KafkaTemplate<String, LocationUpdateEvent>kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void publish(LocationUpdateEvent event){
        kafkaTemplate.send(KafkaTopicConfig.LOCATION_UPDATES, event.trackingNumber(), event)
                .whenComplete((result, error) -> {
                    if(error != null){
                        log.error("Failed to send location update for {}", event.trackingNumber(), error);
                    } else {
                        log.info("Sent location update for {} -> partition{}, offset {}",
                               event.trackingNumber(),
                               result.getRecordMetadata().partition(),
                               result.getRecordMetadata().offset());
                    }
        });
    }
}
