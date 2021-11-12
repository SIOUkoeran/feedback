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
public class Project {
    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "project")
    private List<Register> registerList = new ArrayList<>();


    @Builder
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //== 비즈니스 로직==//
    public Project update(String name, String description) {
        this.name = name;
        this.description = description;
        return this;
    }
}
