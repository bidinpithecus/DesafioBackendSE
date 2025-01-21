package com.bidin.desafio_backend_softexpert.controller;

import com.bidin.desafio_backend_softexpert.dto.OrderResponseDTO;
import com.bidin.desafio_backend_softexpert.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/generate-links")
    public ResponseEntity<List<String>> generateLinks(@RequestBody OrderResponseDTO request) {
        try {
            List<String> paymentLinks = new ArrayList<>();

            request.getShares().forEach((name, value) -> paymentLinks.add(paymentService.generateLink("Sua fatia", new BigDecimal(value), name)));

            return new ResponseEntity<>(paymentLinks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
