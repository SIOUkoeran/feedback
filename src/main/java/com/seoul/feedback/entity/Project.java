package com.seoul.feedback.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "project")
    private List<Register> registerList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project")
    private List<Feedback> feedbackList = new ArrayList<>();


    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @Builder
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = ProjectStatus.CREATE;
    }

    //== 비즈니스 로직==//
    public Project update(String name, String description) {
        this.name = name;
        this.description = description;
        return this;
    }

    public void cancel() {
        this.status = ProjectStatus.CANCEL;
        this.deletedAt = LocalDateTime.now();
    }
}
