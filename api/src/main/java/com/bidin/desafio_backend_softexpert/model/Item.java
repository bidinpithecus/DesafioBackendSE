package com.bidin.desafio_backend_softexpert.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class Item {
    @NotBlank(message = "Nome não deve ser vazio")
    private String name;
    @Positive(message = "Preço deve ser um valor positivo não nulo")
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
