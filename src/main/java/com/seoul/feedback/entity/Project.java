package com.seoul.feedback.entity;

import com.seoul.feedback.dto.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "proejct_member")
    private List<ProjectMember> projectMemberList;

    public Project() {

    }

    @Builder
    public Project(String name, String description, List<ProjectMember> projectMemberList) {
        this.name = name;
        this.description = description;
        this.projectMemberList = projectMemberList;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectMemberList=" + projectMemberList +
                '}';
    }
}
