package com.seoul.feedback.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@ToString
@Getter
public class ProjectMember {
    @Id @GeneratedValue
    @Column(name= "project_member_id")
    private Long id;

    @Column
    private String login;

    public ProjectMember() {
    }

    @Builder
    public ProjectMember(String login) {
        this.login = login;
    }


}
