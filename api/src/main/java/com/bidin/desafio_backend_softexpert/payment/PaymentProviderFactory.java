package com.bidin.desafio_backend_softexpert.payment;

import com.bidin.desafio_backend_softexpert.payment.itau.ItauProvider;
import com.bidin.desafio_backend_softexpert.payment.itau.ItauTokenManager;
import com.bidin.desafio_backend_softexpert.payment.mercadopago.MercadoPagoProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentProviderFactory {
    @Value("${payment.provider}")
    private String paymentProviderType;

    @Value("${mercadopago.access-token}")
    private String mercadoPagoAccessToken;

    @Value("${itau.token-endpoint}")
    private String itauTokenEndpoint;

    @Value("${itau.client-id}")
    private String itauClientId;

    @Value("${itau.client-secret}")
    private String itauClientSecret;

    @Value("${itau.base-api-url}")
    private String itauPaymentEndpoint;

    private final RestTemplate restTemplate;

    public PaymentProviderFactory(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PaymentProvider getPaymentProvider() {
        switch (PaymentProviderType.fromString(paymentProviderType)) {
            case MERCADOPAGO -> {
                return new MercadoPagoProvider(mercadoPagoAccessToken);
            }
            case ITAU -> {
                return new ItauProvider(itauPaymentEndpoint, new ItauTokenManager(itauTokenEndpoint, itauClientId, itauClientSecret, this.restTemplate), this.restTemplate);
            }
            default -> throw new IllegalArgumentException("Método de pagamento não suportado: " + this.paymentProviderType);
        }
    }
}
