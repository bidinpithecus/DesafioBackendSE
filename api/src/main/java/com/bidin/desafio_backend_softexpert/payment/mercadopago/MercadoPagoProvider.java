package com.bidin.desafio_backend_softexpert.payment.mercadopago;

import com.bidin.desafio_backend_softexpert.payment.PaymentProvider;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.yaml.snakeyaml.util.Tuple;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MercadoPagoProvider implements PaymentProvider {
    public MercadoPagoProvider(@Value("${mercadopago.access-token}") String accessToken) {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    @Override
    public Tuple<String, String> generateLink(String description, BigDecimal amount, String name) {
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

            return new Tuple<>(preference.getSandboxInitPoint(), "");
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar pagamento via Mercado Pago", e);
        }
    }
}