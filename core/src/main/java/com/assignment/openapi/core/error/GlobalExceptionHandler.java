package com.assignment.openapi.core.error;

import com.assignment.openapi.core.exception.NetworkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = NetworkException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalModifyingException(NetworkException ex) {
        log.error("handleIllegalModifyingException - ");
        return ErrorResponse.toResponseEntity(ex.getErrorCode());
    }
}
