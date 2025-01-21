package com.bidin.desafio_backend_softexpert.payment;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class MercadoPagoProvider implements PaymentProvider {
    private static final Logger log = LoggerFactory.getLogger(MercadoPagoProvider.class);
    public MercadoPagoProvider(String accessToken) {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    @Override
    public String generateLink(String description, BigDecimal amount, String name) {
        try {
            PreferenceClient client = new PreferenceClient();

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title("Pagamento compartilhado")
                    .description(description)
                    .quantity(1)
                    .currencyId("BRL")
                    .unitPrice(amount)
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .backUrls(
                            PreferenceBackUrlsRequest.builder()
                                    .success("https://test.com/success")
                                    .failure("https://test.com/failure")
                                    .pending("https://test.com/pending")
                                    .build())                    .items(List.of(itemRequest))
                    .payer(PreferencePayerRequest.builder().name(name).build())
                    .autoReturn("all")
                    .build();

            Preference preference = client.create(preferenceRequest);
            return preference.getSandboxInitPoint();
        } catch (Exception e) {
            log.error("Error generating payment link: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao criar pagamento via Mercado Pago", e);
        }
    }
}