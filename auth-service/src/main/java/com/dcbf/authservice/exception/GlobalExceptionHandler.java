package com.dcbf.authservice.exception;

import com.dcbf.authservice.dto.ResponseErrorTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalMessageException.class)
    public ResponseEntity<ResponseErrorTemplate> handleErrorException(GlobalMessageException ex) {
        return ResponseEntity.ok(
                new ResponseErrorTemplate(
                        ex.getMessage(), ex.getCode(), new Object()
                ));
    }
}
