package com.seoul.feedback.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequest {
    private String login;

    public UserCreateRequest(String login) {
        this.login = login;
    }

}
