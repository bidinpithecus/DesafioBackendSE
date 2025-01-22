package com.bidin.desafio_backend_softexpert.payment.itau;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.time.Instant;
import java.util.Map;

@Service
public class ItauTokenManager {
    private final String tokenEndpoint;

    private final String clientId;

    private final String clientSecret;

    private String accessToken;
    private Instant tokenExpiry;

    private final RestTemplate restTemplate;

    public ItauTokenManager(
            @Value("${itau.token-endpoint}") String tokenEndpoint,
            @Value("${itau.client-id}") String clientId,
            @Value("${itau.client-secret}") String clientSecret,
            RestTemplate restTemplate) {
        this.tokenEndpoint = tokenEndpoint;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        if (this.accessToken == null || Instant.now().isAfter(tokenExpiry)) {
            fetchNewToken();
        }

        return this.accessToken;
    }


    public void fetchNewToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String body = "grant_type=client_credentials"
                    + "&client_id=" + clientId
                    + "&client_secret=" + clientSecret;

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(tokenEndpoint, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                this.accessToken = (String) responseBody.get("access_token");
                int expiresIn = (int) responseBody.get("expires_in");
                this.tokenExpiry = Instant.now().plusSeconds((long) (expiresIn * 0.9));
            } else {
                throw new RuntimeException("Falha ao buscar o token de acesso: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o token de acesso: ", e);
        }
    }
}
