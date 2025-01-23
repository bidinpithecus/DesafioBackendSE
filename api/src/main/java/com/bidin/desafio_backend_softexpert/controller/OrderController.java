package com.bidin.desafio_backend_softexpert.controller;

import com.bidin.desafio_backend_softexpert.dto.OrderRequestDTO;
import com.bidin.desafio_backend_softexpert.dto.OrderResponseDTO;
import com.bidin.desafio_backend_softexpert.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/split")
    public ResponseEntity<OrderResponseDTO> split(
        @Valid @RequestBody OrderRequestDTO request
    ) {
        try {
            return new ResponseEntity<>(
                orderService.split(request),
                HttpStatus.OK
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
