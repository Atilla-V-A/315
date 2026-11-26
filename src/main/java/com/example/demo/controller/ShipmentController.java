package com.example.demo.controller;

import com.example.demo.dto.CreateShipmentRequest;
import com.example.demo.dto.LocationUpdateRequest;
import com.example.demo.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/shipments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<?> createShipment(@Valid @RequestBody CreateShipmentRequest request) {
        return new ResponseEntity<>(shipmentService.createShipment(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<?> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(shipmentService.getShipmentByTrackingNumber(trackingNumber));
    }

    @PostMapping("/{trackingNumber}/location")
    public ResponseEntity<?> updateLocation(
            @PathVariable String trackingNumber,
            @Valid @RequestBody LocationUpdateRequest request) {
        shipmentService.publishLocationUpdate(trackingNumber, request);
        return ResponseEntity.accepted().body(Map.of("message", "Konum güncelleme isteği işleme alındı."));
    }
}