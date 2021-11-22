package com.seoul.feedback.dto.response;

import com.seoul.feedback.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {
    private Long userId;
    private String login;

    @Builder
    public UserResponse(User user){
        this.userId = user.getId();
        this.login = user.getLogin();
    }
}
