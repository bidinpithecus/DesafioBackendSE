package com.bidin.desafio_backend_softexpert.service;

import com.bidin.desafio_backend_softexpert.payment.PaymentProviderFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentProviderFactory paymentProviderFactory;

    public PaymentService(PaymentProviderFactory paymentProviderFactory) {
        this.paymentProviderFactory = paymentProviderFactory;
    }

    public String generateLink(String description, BigDecimal amount, String payerEmail) {
        return paymentProviderFactory.getPaymentProvider().generateLink(description, amount, payerEmail);
    }
}
