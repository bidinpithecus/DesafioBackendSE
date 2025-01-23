package com.bidin.desafio_backend_softexpert.dto;

import java.util.Optional;

public class PaymentResponseDTO {

    private final String name;
    private final double amount;
    private String pixLink;
    private String qrCodeBase64;

    public PaymentResponseDTO(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.pixLink = "";
        this.qrCodeBase64 = "";
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getPixLink() {
        return pixLink;
    }

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }

    public void setPixLink(String pixLink) {
        this.pixLink = pixLink;
    }
}
