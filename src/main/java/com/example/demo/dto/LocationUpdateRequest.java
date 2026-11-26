package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationUpdateRequest {
    @NotBlank(message = "Konum bilgisi boş olamaz")
    private String currentLocation;

    @NotBlank(message = "Kargo durumu boş olamaz")
    private String status;
}