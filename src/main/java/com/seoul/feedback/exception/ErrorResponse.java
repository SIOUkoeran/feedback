package com.seoul.feedback.exception;

import lombok.*;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime localDateTime;
    private final HttpStatus httpStatus;
    private final String message;
    private final Throwable throwable;

    @Builder
    public ErrorResponse(Exception e, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = e.getMessage();
        this.throwable = e;
        this.localDateTime = LocalDateTime.now();
    }
}
