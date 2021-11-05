package com.seoul.feedback.entity;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProjectMember {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String login;

    public ProjectMember() {
    }

    @Builder
    public ProjectMember(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ProjectMember{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
