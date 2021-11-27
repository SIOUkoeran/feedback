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
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "_id")
    private Long id;

    @Column
    private String login;

    @OneToMany(mappedBy = "user")
    private List<Register> registerList = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "evalUser")
    private List<Feedback> gaveFeedback = new ArrayList<>();

    @OneToMany(mappedBy = "appraisedUser")
    private List<Feedback> receivedFeedback = new ArrayList<>();

    @Builder
    public User(String login) {
        this.login = login;
    }
}
