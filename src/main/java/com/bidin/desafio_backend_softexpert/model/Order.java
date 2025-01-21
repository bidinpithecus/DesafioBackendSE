package com.bidin.desafio_backend_softexpert.model;

import java.util.List;

public class Order {
    private final List<Item> items;
    private final List<Discount> discounts;
    private final List<Addition> additions;

    public Order(List<Item> items, List<Discount> discounts, List<Addition> additions) {
        this.items = items;
        this.discounts = discounts;
        this.additions = additions;
    }

    public double calculateRawTotal() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    public double calculateTotal() {
        double totalItems = calculateRawTotal();
        double totalDiscounts = discounts.stream().mapToDouble(d -> d.calculate(totalItems)).sum();
        double totalAdditions = additions.stream().mapToDouble(a -> a.calculate(totalItems)).sum();

        return totalItems - totalDiscounts + totalAdditions;
    }
}
