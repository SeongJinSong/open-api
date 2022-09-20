package com.assignment.openapi.core.error.exception;

import com.assignment.openapi.core.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NetworkException extends RuntimeException{
    private final ErrorCode errorCode;
}
