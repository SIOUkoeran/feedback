package com.seoul.feedback.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue
    @Column(name= "user_id")
    private Long id;

    @Column
    private String login;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Register> registerList = new ArrayList<>();

    @Builder
    public User(String login) {
        this.login = login;
    }
}
