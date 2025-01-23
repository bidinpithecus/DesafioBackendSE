package com.bidin.desafio_backend_softexpert.dto;

import com.bidin.desafio_backend_softexpert.model.Addition;
import com.bidin.desafio_backend_softexpert.model.Discount;
import com.bidin.desafio_backend_softexpert.model.Item;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public class OrderAndPaymentRequestDTO {
    @NotNull(message = "Itens não pode ser vazio")
    @NotEmpty(message = "Itens não pode ser vazio")
    @Valid
    private final Map<String, @Valid List<@Valid Item>> items;
    @NotNull(message = "Descontos não pode ser vazio")
    @NotEmpty(message = "Descontos não pode ser vazio")
    private final List<Discount> discounts;
    @NotNull(message = "Adicionais não pode ser vazio")
    @NotEmpty(message = "Adicionais não pode ser vazio")
    private final List<Addition> additions;
    @NotNull(message = "Nome do proprietário não pode ser vazio")
    @NotBlank(message = "Nome do proprietário não pode ser em vazio")
    private final String ownerName;
    @NotNull(message = "Chave PIX do proprietário não pode ser vazio")
    @NotBlank(message = "Chave PIX do proprietário não pode ser em vazio")
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
