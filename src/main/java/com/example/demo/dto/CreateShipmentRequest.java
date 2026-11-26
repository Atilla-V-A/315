package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateShipmentRequest {
    @NotBlank(message = "Gönderici adı boş olamaz")
    private String senderName;

    @NotBlank(message = "Alıcı adı boş olamaz")
    private String receiverName;

    @NotBlank(message = "Varış adresi boş olamaz")
    private String destinationAddress;
}