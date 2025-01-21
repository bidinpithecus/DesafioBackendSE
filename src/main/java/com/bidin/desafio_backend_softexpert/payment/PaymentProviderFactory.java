package com.bidin.desafio_backend_softexpert.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentProviderFactory {
    @Value("${payment.provider}")
    private String paymentProviderType;

    @Value("${mercadopago.access-token}")
    private String mercadoPagoAccessToken;

    public PaymentProvider getPaymentProvider() {
        switch (PaymentProviderType.fromString(paymentProviderType)) {
            case MERCADOPAGO -> {
                return new MercadoPagoProvider(mercadoPagoAccessToken);
            }
            case PICPAY -> {
                return new PicpayProvider();
            }
            default -> throw new IllegalArgumentException("Método de pagamento não suportado: " + this.paymentProviderType);
        }
    }
}
