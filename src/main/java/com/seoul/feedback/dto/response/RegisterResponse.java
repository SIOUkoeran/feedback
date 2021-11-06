package com.seoul.feedback.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RegisterResponse {
    private Long id;
    private String login;
    private Long projectId;

    @Builder
    public RegisterResponse(Long id, String login, Long projectId) {
        this.id = id;
        this.login = login;
        this.projectId = projectId;
    }

}
