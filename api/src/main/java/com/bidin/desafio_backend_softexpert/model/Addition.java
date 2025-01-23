package com.bidin.desafio_backend_softexpert.model;

public class Addition {

    private CostType type;
    private double amount;

    public Addition(CostType type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public CostType getType() {
        return type;
    }

    public void setType(CostType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double calculate(double total) {
        if (this.type == CostType.FIXED) {
            return this.amount;
        } else if (this.type == CostType.PERCENTAGE) {
            return (total * (this.amount / 100.0));
        }

        return 0.0;
    }
}
