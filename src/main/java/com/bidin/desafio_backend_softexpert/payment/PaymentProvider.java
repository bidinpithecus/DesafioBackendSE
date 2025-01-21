package com.bidin.desafio_backend_softexpert.payment;

import java.math.BigDecimal;

public interface PaymentProvider {
    String generateLink(String description, BigDecimal amount, String payerEmail);
}
