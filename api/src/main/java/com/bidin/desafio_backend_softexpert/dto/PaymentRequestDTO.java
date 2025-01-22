package com.bidin.desafio_backend_softexpert.dto;

import java.util.Map;

public class PaymentRequestDTO {
    private final Map<String, Double> shares;
    private final String ownerName;
    private final String ownerPixKey;

    public PaymentRequestDTO(Map<String, Double> shares, String ownerName, String ownerPixKey) {
        this.shares = shares;
        this.ownerName = ownerName;
        this.ownerPixKey = ownerPixKey;
    }

    public Map<String, Double> getShares() {
        return shares;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getOwnerPixKey() {
        return this.ownerPixKey;
    }
}
