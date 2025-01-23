package com.bidin.desafio_backend_softexpert.controller;

import com.bidin.desafio_backend_softexpert.dto.PaymentRequestDTO;
import com.bidin.desafio_backend_softexpert.dto.PaymentResponseDTO;
import com.bidin.desafio_backend_softexpert.service.PaymentService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/generate-links")
    public ResponseEntity<List<PaymentResponseDTO>> generateLinks(
        @Valid @RequestBody PaymentRequestDTO request
    ) {
        try {
            List<PaymentResponseDTO> response = new ArrayList<>(2);

            request
                .getShares()
                .forEach((name, value) -> {
                    PaymentResponseDTO user = new PaymentResponseDTO(
                        name,
                        value
                    );
                    if (!name.equals(request.getOwnerName())) {
                        var paymentInfo = paymentService.generateLink(
                            "Sua fatia",
                            new BigDecimal(value),
                            request.getOwnerPixKey()
                        );
                        user.setPixLink(paymentInfo._1());
                        user.setQrCodeBase64(paymentInfo._2());
                    }
                    response.add(user);
                });

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
