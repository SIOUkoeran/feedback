package com.seoul.feedback.dto.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateRequest {

    private String login;

    public UserCreateRequest(String login) {
        this.login = login;
    }
}
