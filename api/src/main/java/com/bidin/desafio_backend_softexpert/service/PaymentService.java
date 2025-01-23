package com.bidin.desafio_backend_softexpert.service;

import com.bidin.desafio_backend_softexpert.payment.PaymentProvider;
import com.bidin.desafio_backend_softexpert.payment.PaymentProviderFactory;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.Tuple;

@Service
public class PaymentService {

    private final PaymentProvider paymentProvider;

    public PaymentService(PaymentProviderFactory paymentProviderFactory) {
        this.paymentProvider = paymentProviderFactory.getPaymentProvider();
    }

    public Tuple<String, String> generateLink(
        String description,
        BigDecimal amount,
        String payerInfo
    ) {
        return paymentProvider.generateLink(description, amount, payerInfo);
    }
}
