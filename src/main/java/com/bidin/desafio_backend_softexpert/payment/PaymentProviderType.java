package com.bidin.desafio_backend_softexpert.payment;

public enum PaymentProviderType {
    MERCADOPAGO,
    PICPAY;

    public static PaymentProviderType fromString(String value) {
        try {
            return PaymentProviderType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Método de pagamento não autorizado: " + value, e);
        }
    }
}