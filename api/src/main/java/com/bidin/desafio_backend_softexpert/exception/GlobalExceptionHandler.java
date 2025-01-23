package com.bidin.desafio_backend_softexpert.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        ex
            .getBindingResult()
            .getFieldErrors()
            .forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
        IllegalArgumentException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormatException(
        InvalidFormatException ex
    ) {
        Map<String, String> error = new HashMap<>();
        if (ex.getTargetType().isEnum()) {
            String allowedValues = Arrays.toString(
                ((Class<
                            ? extends Enum<?>
                        >) ex.getTargetType()).getEnumConstants()
            );
            allowedValues = allowedValues
                .replaceAll("[\\[\\]]", "")
                .replaceAll(",", ""); // Formata a string
            error.put(
                "error",
                "Valor inválido: apenas " + allowedValues + " são permitidos."
            );
        } else {
            error.put("error", "Formato inválido para o campo.");
        }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
