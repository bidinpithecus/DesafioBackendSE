package com.bidin.desafio_backend_softexpert.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Map;

public class PaymentRequestDTO {

    @NotNull(message = "Itens não pode ser vazio")
    @NotEmpty(message = "Itens não pode ser vazio")
    @Valid
    private final Map<@NotBlank String, @Positive Double> shares;

    @NotNull(message = "Nome do proprietário não pode ser vazio")
    @NotBlank(message = "Nome do proprietário não pode ser em vazio")
    private final String ownerName;

    @NotNull(message = "Chave PIX do proprietário não pode ser vazio")
    @NotBlank(message = "Chave PIX do proprietário não pode ser em vazio")
    private final String ownerPixKey;

    public PaymentRequestDTO(
        Map<String, Double> shares,
        String ownerName,
        String ownerPixKey
    ) {
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
