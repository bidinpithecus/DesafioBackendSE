package com.bidin.desafio_backend_softexpert.payment;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import java.math.BigDecimal;
import java.util.List;

public class PicpayProvider implements PaymentProvider {
    public PicpayProvider() {

    }

    @Override
    public String generateLink(String description, BigDecimal amount, String payerEmail) {
        return "https://picpay.com/payment-link";
    }
}
