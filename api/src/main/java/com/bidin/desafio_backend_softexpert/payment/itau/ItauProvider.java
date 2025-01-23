package com.bidin.desafio_backend_softexpert.payment.itau;

import com.bidin.desafio_backend_softexpert.payment.PaymentProvider;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.Tuple;

public class ItauProvider implements PaymentProvider {

    private final String paymentEndpoint;

    private final ItauTokenManager tokenManager;
    private final RestTemplate restTemplate;

    public ItauProvider(
        @Value("${itau.base-api-url}") String paymentEndpoint,
        ItauTokenManager tokenManager,
        RestTemplate restTemplate
    ) {
        this.paymentEndpoint = paymentEndpoint;
        this.tokenManager = tokenManager;
        this.restTemplate = restTemplate;
    }

    @Override
    public Tuple<String, String> generateLink(
        String _description,
        BigDecimal amount,
        String pixKey
    ) {
        try {
            Map<String, Object> requestBody;
            requestBody = new HashMap<>();
            requestBody.put("valor", Map.of("original", amount));
            requestBody.put("chave", pixKey);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(
                "Authorization",
                "Bearer " + tokenManager.getAccessToken()
            );

            HttpEntity<Map<String, Object>> postEntity = new HttpEntity<>(
                requestBody,
                headers
            );

            ResponseEntity<Map> postResponse = restTemplate.exchange(
                paymentEndpoint + "/cob",
                HttpMethod.POST,
                postEntity,
                Map.class
            );

            if (postResponse.getStatusCode() == HttpStatus.OK) {
                var postResponseBody = postResponse.getBody();
                assert postResponseBody != null;

                String txid = postResponseBody.get("txid").toString();
                assert txid != null;

                HttpEntity<Map<String, Object>> getEntity = new HttpEntity<>(
                    new HashMap<>(),
                    headers
                );

                ResponseEntity<Map> getResponse = restTemplate.exchange(
                    paymentEndpoint + "/cob" + "/" + txid + "/qrcode",
                    HttpMethod.GET,
                    getEntity,
                    Map.class
                );

                if (getResponse.getStatusCode() == HttpStatus.OK) {
                    var getResponseBody = getResponse.getBody();
                    assert getResponseBody != null;

                    return new Tuple<>(
                        getResponseBody.get("pix_link").toString(),
                        getResponseBody.get("imagem_base64").toString()
                    );
                } else {
                    throw new RuntimeException(
                        "Falha ao gerar qrcode: " + postResponse.getStatusCode()
                    );
                }
            } else {
                throw new RuntimeException(
                    "Falha ao gerar pagamento: " + postResponse.getStatusCode()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na comunicacao com Itau API", e);
        }
    }
}
