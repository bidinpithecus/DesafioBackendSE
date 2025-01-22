package com.bidin.desafio_backend_softexpert.controller;

import com.bidin.desafio_backend_softexpert.dto.PaymentRequestDTO;
import com.bidin.desafio_backend_softexpert.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/generate-links")
    public ResponseEntity<Map<String, List<String>>> generateLinks(@RequestBody PaymentRequestDTO request) {
        try {
            Map<String, List<String>> shares = new HashMap<>();

            request.getShares().forEach((name, value) -> {
                if (!name.equals(request.getOwnerName())) {
                    shares.put(name, paymentService.generateLink("Sua fatia", new BigDecimal(value), request.getOwnerPixKey()));
                }
            });

            return new ResponseEntity<>(shares, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
