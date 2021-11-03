package com.seoul.feedback.dto;

import lombok.Getter;

@Getter
public class User {
    String login;
    String displayname;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", displayname='" + displayname + '\'' +
                '}';
    }
}
