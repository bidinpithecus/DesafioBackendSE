package com.bidin.desafio_backend_softexpert.controller;

import com.bidin.desafio_backend_softexpert.dto.*;
import com.bidin.desafio_backend_softexpert.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
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
    public ResponseEntity<List<PaymentResponseDTO>> splitAndGenerateLinks(@RequestBody OrderAndPaymentRequestDTO request) {
        try {
            ResponseEntity<OrderResponseDTO> orderResponse = restTemplate.exchange(
                    "http://localhost:8080/api/split",
                    HttpMethod.POST,
                    new HttpEntity<>(new OrderRequestDTO(request.getItems(), request.getDiscounts(), request.getAdditions())),
                    OrderResponseDTO.class
            );

            if (orderResponse.getStatusCode() != HttpStatus.OK) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            PaymentRequestDTO paymentRequest = new PaymentRequestDTO(Objects.requireNonNull(orderResponse.getBody()).getShares(), request.getOwnerName(), request.getOwnerPixKey());

            return restTemplate.exchange(
                    "http://localhost:8080/api/generate-links",
                    HttpMethod.POST,
                    new HttpEntity<>(paymentRequest),
                    new ParameterizedTypeReference<>() {}
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
