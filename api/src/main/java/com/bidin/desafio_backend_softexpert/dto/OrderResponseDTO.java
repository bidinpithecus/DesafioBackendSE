package com.bidin.desafio_backend_softexpert.dto;

import java.util.Map;

public class OrderResponseDTO {

    private Map<String, Double> shares;

    public OrderResponseDTO(Map<String, Double> shares) {
        this.shares = shares;
    }

    public Map<String, Double> getShares() {
        return shares;
    }

    public void setShares(Map<String, Double> shares) {
        this.shares = shares;
    }
}
