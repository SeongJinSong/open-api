package com.assignment.openapi.web.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @param statusCode http response code
 * @param message    result message
 * @param data       result data
 */
@JsonPropertyOrder({"statusCode", "message", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseWrapper<T>(int statusCode, String message, T data) {
    public static <T> ResponseEntity<ResponseWrapper<T>> ok(T data, String message) {
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), message, data));
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> created(T data, String message) {
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.CREATED.value(), message, data));
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> deleted(String message) {
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), message, null));
    }
}