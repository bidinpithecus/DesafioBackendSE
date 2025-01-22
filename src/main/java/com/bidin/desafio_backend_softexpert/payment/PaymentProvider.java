package com.bidin.desafio_backend_softexpert.payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentProvider {
    List<String> generateLink(String description, BigDecimal amount, String payerInfo);
}
