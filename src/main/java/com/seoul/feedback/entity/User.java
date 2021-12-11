package com.seoul.feedback.entity;

import com.seoul.feedback.entity.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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


    @OneToMany(mappedBy = "evalUser", cascade = CascadeType.ALL)
    private List<Feedback> gaveFeedback = new ArrayList<>();

    @OneToMany(mappedBy = "appraisedUser", cascade = CascadeType.ALL)
    private List<Feedback> receivedFeedback = new ArrayList<>();


    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String login, Role role) {
        this.login = login;
        this.role = role;
    }

    public User(String login, Long id){
        this.login = login;
        this.id = id;
    }
    public User(String login, Long id, Role role){
        this.login = login;
        this.id = id;
        this.role = role;
    }
    public String getRoleKey() {
        return role.getKey();
    }
}
