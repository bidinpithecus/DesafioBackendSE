package com.bidin.desafio_backend_softexpert.dto;

import com.bidin.desafio_backend_softexpert.model.Addition;
import com.bidin.desafio_backend_softexpert.model.Discount;
import com.bidin.desafio_backend_softexpert.model.Item;

import java.util.List;
import java.util.Map;

public class OrderAndPaymentRequestDTO {
    private final Map<String, List<Item>> items;
    private final List<Discount> discounts;
    private final List<Addition> additions;
    private final String ownerName;
    private final String ownerPixKey;

    public OrderAndPaymentRequestDTO(Map<String, List<Item>> items, List<Discount> discounts, List<Addition> additions, String ownerName, String ownerPixKey) {
        this.items = items;
        this.discounts = discounts;
        this.additions = additions;
        this.ownerName = ownerName;
        this.ownerPixKey = ownerPixKey;
    }

    public Map<String, List<Item>> getItems() {
        return items;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public List<Addition> getAdditions() {
        return additions;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerPixKey() {
        return ownerPixKey;
    }
}
