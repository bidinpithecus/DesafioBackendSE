package com.bidin.desafio_backend_softexpert.controller;

import com.bidin.desafio_backend_softexpert.dto.*;
import com.bidin.desafio_backend_softexpert.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class OrderAndPaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/split-and-generate-links")
    public ResponseEntity<?> splitAndGenerateLinks(@Valid @RequestBody OrderAndPaymentRequestDTO request) {
        try {
            ResponseEntity<OrderResponseDTO> orderResponse;
            try {
                orderResponse = restTemplate.exchange(
                        "http://localhost:8080/api/split",
                        HttpMethod.POST,
                        new HttpEntity<>(new OrderRequestDTO(request.getItems(), request.getDiscounts(), request.getAdditions())),
                        OrderResponseDTO.class
                );
            } catch (HttpClientErrorException.BadRequest e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(e.getResponseBodyAsString());
            }

            if (orderResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            PaymentRequestDTO paymentRequest = new PaymentRequestDTO(
                    Objects.requireNonNull(orderResponse.getBody()).getShares(),
                    request.getOwnerName(),
                    request.getOwnerPixKey()
            );

            ResponseEntity<List<PaymentResponseDTO>> paymentResponse = restTemplate.exchange(
                    "http://localhost:8080/api/generate-links",
                    HttpMethod.POST,
                    new HttpEntity<>(paymentRequest),
                    new ParameterizedTypeReference<>() {}
            );

            return paymentResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
