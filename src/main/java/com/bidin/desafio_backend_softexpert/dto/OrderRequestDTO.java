package com.bidin.desafio_backend_softexpert.dto;

import com.bidin.desafio_backend_softexpert.model.Addition;
import com.bidin.desafio_backend_softexpert.model.Discount;
import com.bidin.desafio_backend_softexpert.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderRequestDTO {
    private final Map<String, List<Item>> items;
    private final List<Discount> discounts;
    private final List<Addition> additions;

    public OrderRequestDTO(Map<String, List<Item>> items, List<Discount> discounts, List<Addition> additions) {
        this.items = items;
        this.discounts = discounts;
        this.additions = additions;
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

    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        this.items.values().forEach(allItems::addAll);

        return allItems;
    }
}
