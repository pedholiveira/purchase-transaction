package com.test.wex.transaction.controller.advice;

import com.test.wex.transaction.dto.ErrorResponse;
import com.test.wex.transaction.exception.PurchaseCannotBeConvertedException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PurchaseCannotBeConvertedException.class)
    public ResponseEntity<ErrorResponse> badRequests(Exception exception) {
        var errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.BAD_REQUEST.toString())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationException(
            ConstraintViolationException exception, WebRequest request) {
        var errors = exception.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        this::getPropertyPath,
                        ConstraintViolation::getMessage
                ));

        var response = ErrorResponse.builder()
                .errors(errors)
                .code(HttpStatus.BAD_REQUEST.toString())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String getPropertyPath(ConstraintViolation<?> exception) {
        return exception.getPropertyPath().toString();
    }
}
