package com.bidin.desafio_backend_softexpert.service;

import static com.bidin.desafio_backend_softexpert.utilities.Math.round;

import com.bidin.desafio_backend_softexpert.dto.OrderRequestDTO;
import com.bidin.desafio_backend_softexpert.dto.OrderResponseDTO;
import com.bidin.desafio_backend_softexpert.model.Item;
import com.bidin.desafio_backend_softexpert.model.Order;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private Order convertToDomain(OrderRequestDTO request) {
        return new Order(
            request.getAllItems(),
            request.getDiscounts(),
            request.getAdditions()
        );
    }

    private double calculateTotalOrder(OrderRequestDTO request) {
        return convertToDomain(request).calculateTotal();
    }

    private double calculateRawTotal(OrderRequestDTO request) {
        return convertToDomain(request).calculateRawTotal();
    }

    public OrderResponseDTO split(OrderRequestDTO request) {
        Map<String, Double> shares = new HashMap<>();

        double percentageOverTotalCost =
            calculateTotalOrder(request) / calculateRawTotal(request);

        request
            .getItems()
            .forEach((name, items) ->
                shares.put(
                    name,
                    round(
                        items.stream().mapToDouble(Item::getPrice).sum() *
                        percentageOverTotalCost,
                        2
                    )
                )
            );

        return new OrderResponseDTO(shares);
    }
}
