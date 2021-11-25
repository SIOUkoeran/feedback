package com.seoul.feedback.dto.response;

import com.seoul.feedback.entity.Register;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterResponse {
    private Long registerId;
    private String login;
    private Long projectId;


    @Builder
    public RegisterResponse(Register register) {
        this.registerId = register.getId();
        this.login = register.getUser().getLogin();
        this.projectId = register.getProject().getId();
    }

}
