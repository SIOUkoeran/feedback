package com.seoul.feedback.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Project {
    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String description;


    @Builder
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
