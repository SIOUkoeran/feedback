package com.seoul.feedback.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Project {
    @Id @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private List<ProjectMember> projectMemberList;

    public Project() {

    }

    // for 멤버 신청 & 승인
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.projectMemberList = new ArrayList<>();
    }

    @Builder
    public Project(String name, String description, List<ProjectMember> projectMemberList) {
        this.name = name;
        this.description = description;
        this.projectMemberList = projectMemberList;
    }

}
