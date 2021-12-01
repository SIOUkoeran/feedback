package com.seoul.feedback.entity;

import com.seoul.feedback.entity.enums.RegisterStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Register {

    @Id @GeneratedValue
    @Column(name="_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private RegisterStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    //== 연관관계 편의 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getRegisterList().add(this);
    }

    private void setProject(Project project) {
        this.project = project;
        project.getRegisterList().add(this);
    }

    private void setStatus(RegisterStatus status) {
        this.status = status;
    }

    //== 생성 메서드 ==//
    public static Register createRegister(User user, Project project) {
        Register register = new Register();
        register.setUser(user);
        register.setProject(project);
        register.setStatus(RegisterStatus.REGISTER);
        return register;
    }

    //== 비즈니스 메서드 ==//
    public void cancel() {
        if (this.status != RegisterStatus.CANCEL) {
            this.status = RegisterStatus.CANCEL;
            this.deletedAt = LocalDateTime.now();
        }
    }

}
