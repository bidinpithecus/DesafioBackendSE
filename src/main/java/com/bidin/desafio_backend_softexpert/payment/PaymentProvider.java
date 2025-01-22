package com.bidin.desafio_backend_softexpert.payment;

import org.yaml.snakeyaml.util.Tuple;

import java.math.BigDecimal;

public interface PaymentProvider {
    Tuple<String, String> generateLink(String description, BigDecimal amount, String payerInfo);
}
