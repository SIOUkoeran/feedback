package com.seoul.feedback.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id @GeneratedValue
    @Column(name= "user_id")
    private Long id;

    @Column
    private String login;

    @OneToMany(mappedBy = "user")
    private List<Register> registerList = new ArrayList<>();

    @Builder
    public User(String login) {
        this.login = login;
    }
}
