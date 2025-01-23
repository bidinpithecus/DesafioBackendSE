package com.bidin.desafio_backend_softexpert.payment;

import java.math.BigDecimal;
import org.yaml.snakeyaml.util.Tuple;

public interface PaymentProvider {
    Tuple<String, String> generateLink(
        String description,
        BigDecimal amount,
        String payerInfo
    );
}
