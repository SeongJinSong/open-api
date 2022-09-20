package com.assignment.openapi.core.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    REQUEST_TIMEOUT_EXCEPTION(HttpStatus.REQUEST_TIMEOUT, "request timeout"), ;

    private final HttpStatus httpStatus;
    private final String messageDetails;
}
