package com.bidin.desafio_backend_softexpert.controller;

import com.bidin.desafio_backend_softexpert.dto.*;
import com.bidin.desafio_backend_softexpert.service.OrderService;
import com.bidin.desafio_backend_softexpert.service.PaymentService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderAndPaymentController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/split")
    public ResponseEntity<List<PaymentResponseDTO>> splitAndGenerateLinks(
        @Valid @RequestBody OrderAndPaymentRequestDTO request
    ) {
        try {
            PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(
                orderService
                    .split(
                        new OrderRequestDTO(
                            request.getItems(),
                            request.getDiscounts(),
                            request.getAdditions()
                        )
                    )
                    .getShares(),
                request.getOwnerName(),
                request.getOwnerPixKey()
            );

            List<PaymentResponseDTO> response = new ArrayList<>(2);

            paymentRequestDTO
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
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
            ).build();
        }
    }
}
