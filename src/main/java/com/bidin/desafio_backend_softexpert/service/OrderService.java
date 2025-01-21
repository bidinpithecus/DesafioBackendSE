package com.bidin.desafio_backend_softexpert.service;

import com.bidin.desafio_backend_softexpert.dto.OrderResponseDTO;
import com.bidin.desafio_backend_softexpert.dto.OrderRequestDTO;
import com.bidin.desafio_backend_softexpert.model.Item;
import com.bidin.desafio_backend_softexpert.model.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {
    private Order convertToDomain(OrderRequestDTO request) {
        return new Order(request.getAllItems(), request.getDiscounts(), request.getAdditions());
    }

    private double calculateTotalOrder(OrderRequestDTO request) {
        return convertToDomain(request).calculateTotal();
    }

    private double calculateRawTotal(OrderRequestDTO request) {
        return convertToDomain(request).calculateRawTotal();
    }

    public OrderResponseDTO split(OrderRequestDTO request) {
        Map<String, Double> shares = new HashMap<>();

        double percentageOverTotalCost = calculateTotalOrder(request) / calculateRawTotal(request);

        request.getItems().forEach((name, items) -> shares.put(name, items.stream().mapToDouble(Item::getPrice).sum() * percentageOverTotalCost));

        return new OrderResponseDTO(shares);
    }
}
