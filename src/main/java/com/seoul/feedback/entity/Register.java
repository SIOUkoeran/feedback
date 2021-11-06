package com.seoul.feedback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Register {

    @Id @GeneratedValue
    @Column(name="join_id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    //== 연관관계 편의 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getRegisterList().add(this);
    }

    public void setProject(Project project) {
        this.project = project;
        project.getRegisterList().add(this);
    }

    //== 생성 메서드 ==//
    public static Register createRegister(User user, Project project) {
        Register register = new Register();
        register.setUser(user);
        register.setProject(project);
        return register;
    }

}
