package com.seoul.feedback.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse {

    private Integer statusCode;
    private String message;

    @Builder
    public CommonResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
