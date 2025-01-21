package com.bidin.desafio_backend_softexpert.service;

import com.bidin.desafio_backend_softexpert.dto.OrderRequestDTO;
import com.bidin.desafio_backend_softexpert.dto.OrderResponseDTO;
import com.bidin.desafio_backend_softexpert.model.Addition;
import com.bidin.desafio_backend_softexpert.model.CostType;
import com.bidin.desafio_backend_softexpert.model.Discount;
import com.bidin.desafio_backend_softexpert.model.Item;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private final OrderService orderService = new OrderService();

    @Test
    void testSplit() {
        Map<String, List<Item>> items = new HashMap<>();
        items.put("Voce", Arrays.asList(new Item("Hamburguer", 40.00), new Item("Sobremesa", 2.00)));
        items.put("Amigo", List.of(new Item("Sanduiche", 8.00)));

        List<Discount> discounts = List.of(new Discount(CostType.FIXED, 20.00));
        List<Addition> additions = List.of(new Addition(CostType.FIXED, 8.00));

        OrderRequestDTO request = new OrderRequestDTO(items, discounts, additions);
        OrderResponseDTO response = orderService.split(request);

        Map<String, Double> expectedShares = new HashMap<>();
        expectedShares.put("Voce", 31.92);
        expectedShares.put("Amigo", 6.08);

        assertEquals(expectedShares.size(), response.getShares().size());
        response.getShares().forEach((name, amount) -> assertEquals(expectedShares.get(name), amount, 0.01));
    }

    @Test
    void testSplit_NoDiscountsOrAdditions() {
        Map<String, List<Item>> items = new HashMap<>();
        items.put("Voce", List.of(new Item("Pizza", 50.00)));
        items.put("Amigo", Arrays.asList(new Item("Burger", 30.00), new Item("Fries", 20.00)));

        OrderRequestDTO request = new OrderRequestDTO(items, List.of(), List.of());

        OrderResponseDTO response = orderService.split(request);

        assertEquals(50.00, response.getShares().get("Voce"), 0.01);
        assertEquals(50.00, response.getShares().get("Amigo"), 0.01);
    }

    @Test
    void testSplit_SingleParticipant() {
        Map<String, List<Item>> items = new HashMap<>();
        items.put("Voce", List.of(new Item("Sushi", 100.00)));

        OrderRequestDTO request = new OrderRequestDTO(items, List.of(), List.of(new Addition(CostType.FIXED, 10.00)));

        OrderResponseDTO response = orderService.split(request);

        assertEquals(110.00, response.getShares().get("Voce"), 0.01);
    }

    @Test
    void testSplit_NoItems() {
        OrderRequestDTO request = new OrderRequestDTO(Map.of(), List.of(), List.of());

        OrderResponseDTO response = orderService.split(request);

        assertTrue(response.getShares().isEmpty());
    }

    @Test
    void testSplit_OnlyDiscounts() {
        Map<String, List<Item>> items = new HashMap<>();
        items.put("Voce", List.of(new Item("Pasta", 60.00)));
        items.put("Amigo", List.of(new Item("Steak", 40.00)));

        List<Discount> discounts = List.of(new Discount(CostType.FIXED, 20.00));

        OrderRequestDTO request = new OrderRequestDTO(items, discounts, List.of());

        OrderResponseDTO response = orderService.split(request);

        assertEquals(48.00, response.getShares().get("Voce"), 0.01);
        assertEquals(32.00, response.getShares().get("Amigo"), 0.01);
    }

    @Test
    void testSplit_MixedDiscountsAndAdditions() {
        Map<String, List<Item>> items = new HashMap<>();
        items.put("Voce", List.of(new Item("Pizza", 50.00)));
        items.put("Amigo", Arrays.asList(new Item("Burger", 30.00), new Item("Fries", 20.00)));

        List<Discount> discounts = List.of(new Discount(CostType.PERCENTAGE, 10.00));
        List<Addition> additions = List.of(new Addition(CostType.FIXED, 20.00));

        OrderRequestDTO request = new OrderRequestDTO(items, discounts, additions);

        OrderResponseDTO response = orderService.split(request);

        assertEquals(55.00, response.getShares().get("Voce"), 0.01);
        assertEquals(55.00, response.getShares().get("Amigo"), 0.01);
    }

    @Test
    void testSplit_ExtremeValues() {
        Map<String, List<Item>> items = new HashMap<>();
        items.put("Voce", List.of(new Item("Luxury Item", 1_000_000.00)));
        items.put("Amigo", List.of(new Item("Cheap Item", 0.10)));

        List<Addition> additions = List.of(new Addition(CostType.FIXED, 500.00));

        OrderRequestDTO request = new OrderRequestDTO(items, List.of(), additions);

        OrderResponseDTO response = orderService.split(request);

        assertEquals(1_000_500.00, response.getShares().get("Voce"), 0.01);
        assertEquals(0.10, response.getShares().get("Amigo"), 0.01);
    }

}
