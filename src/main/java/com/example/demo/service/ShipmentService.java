package com.example.demo.service;

import com.example.demo.dto.CreateShipmentRequest;
import com.example.demo.dto.LocationUpdateRequest;
import com.example.demo.exception.ShipmentNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShipmentService {

    // Veritabani baglanana kadar gecici hafiza (In-Memory)
    private final Map<String, Map<String, Object>> shipmentStore = new ConcurrentHashMap<>();

    public Map<String, Object> createShipment(CreateShipmentRequest request) {
        String trackingNumber = "TRK-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        Map<String, Object> shipment = new HashMap<>();
        shipment.put("trackingNumber", trackingNumber);
        shipment.put("senderName", request.getSenderName());
        shipment.put("receiverName", request.getReceiverName());
        shipment.put("destinationAddress", request.getDestinationAddress());
        shipment.put("status", "CREATED");
        shipment.put("currentLocation", "Merkez Depo");
        shipment.put("history", new ArrayList<>(List.of(
                Map.of("location", "Merkez Depo", "status", "CREATED", "time", LocalDateTime.now().toString()))));

        shipmentStore.put(trackingNumber, shipment);
        return shipment;
    }

    public List<Map<String, Object>> getAllShipments() {
        return new ArrayList<>(shipmentStore.values());
    }

    public Map<String, Object> getShipmentByTrackingNumber(String trackingNumber) {
        Map<String, Object> shipment = shipmentStore.get(trackingNumber);
        if (shipment == null) {
            throw new ShipmentNotFoundException("Kargo bulunamadı: " + trackingNumber);
        }
        return shipment;
    }

    @SuppressWarnings("unchecked")
    public void publishLocationUpdate(String trackingNumber, LocationUpdateRequest request) {
        Map<String, Object> shipment = getShipmentByTrackingNumber(trackingNumber);
        
        // TODO: Kafka katmani geldiginde burasi Kafka Producer'a mesaj atacak
        shipment.put("currentLocation", request.getCurrentLocation());
        shipment.put("status", request.getStatus());
        
        List<Map<String, Object>> history = (List<Map<String, Object>>) shipment.get("history");
        history.add(Map.of(
            "location", request.getCurrentLocation(),
            "status", request.getStatus(),
            "time", LocalDateTime.now().toString()
        ));
    }
}