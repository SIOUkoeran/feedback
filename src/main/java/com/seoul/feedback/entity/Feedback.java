package com.seoul.feedback.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Feedback {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User appraisedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User evalUser;

    @ManyToOne
    private Project project;

    @Enumerated(EnumType.STRING)
    private FeedbackStatus feedbackStatus = FeedbackStatus.REGISTER;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime deletedAt;

    @NotNull
    private String message;

    @NotNull
    @Range(min = 1, max = 5)
    private int star;


    public static Feedback createFeedback(User evalUser, User appraisedUser, String message, int star, Project project)
    {
        Feedback feedback = new Feedback();
        feedback.setEvalUser(evalUser);
        feedback.setAppraisedUser(appraisedUser);
        feedback.setProject(project);
        feedback.setMessage(message, star);
        return feedback;
    }

    public void setMessage(String message, int star){
        this.message = message;
        this.star = star;
        this.feedbackStatus = FeedbackStatus.REGISTER;
    }
    public void setProject(Project project){
        this.project = project;
        project.getFeedbackList().add(this);
    }
    public void setEvalUser(User user){
        this.evalUser = user;
        user.getGaveFeedback().add(this);
    }

    public void setAppraisedUser(User user){
        this.appraisedUser = user;
        user.getReceivedFeedback().add(this);
    }

    public void cancel(){
        if (feedbackStatus != FeedbackStatus.CANCEL) {
            this.feedbackStatus = FeedbackStatus.CANCEL;
            this.deletedAt = LocalDateTime.now();
        }
    }
}
