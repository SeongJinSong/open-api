package com.assignment.openapi.core.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {
    private final int code; // 200
    private final String status; // OK
    private final String message; // reason

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime localDateTime = LocalDateTime.now();



    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .code(errorCode.getHttpStatus().value())
                        .status(errorCode.getHttpStatus().name())
                        .message(errorCode.getMessageDetails())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(String message) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ErrorResponse.builder()
                        .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .status(HttpStatus.SERVICE_UNAVAILABLE.toString())
                        .message(message)
                        .build()
                );
    }
}
