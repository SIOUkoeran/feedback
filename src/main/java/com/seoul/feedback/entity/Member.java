package com.seoul.feedback.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Member {
    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Member(Team team, User user) {
        this.team = team;
        this.user = user;
    }
}
