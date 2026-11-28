package com.example.demo.messaging.event;
import java.time.Instant;
import java.util.UUID;
public record LocationUpdateEvent(
        String eventId,
        String trackingNumber,
        String location,
        String status,
        Instant occuredAt)
{
    public static LocationUpdateEvent of(String trackingNumber, String location, String status){
        return new LocationUpdateEvent(
                UUID.randomUUID().toString(), trackingNumber, location, status, Instant.now()
        );
    }

}