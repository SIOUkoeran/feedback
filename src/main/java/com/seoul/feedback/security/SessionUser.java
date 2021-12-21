package com.seoul.feedback.security;

import com.seoul.feedback.entity.User;
import com.seoul.feedback.entity.enums.Role;
import lombok.Getter;


import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {


    private String login;
    private Long id;
    private Role role;

    public SessionUser(){}

    public SessionUser(User user) {
        this.login = user.getLogin();
        this.id = user.getId();
        this.role = user.getRole();
    }
}
