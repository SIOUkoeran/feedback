package com.seoul.feedback.dto;

import lombok.Getter;

@Getter
public class Token {
    String access_token;
    String token_type;
    Long expires_in;
    String refresh_token;
    String scope;
    Long created_at;

    @Override
    public String toString() {
        return "Token{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
