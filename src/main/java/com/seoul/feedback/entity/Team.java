package com.seoul.feedback.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Team {
    @Id @GeneratedValue
    @Column(name= "team_id")
    private Long id;

    @OneToMany(mappedBy = "team")
    private List<Member> memberList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Team(Project project) {
        this.project = project;
    }
}
