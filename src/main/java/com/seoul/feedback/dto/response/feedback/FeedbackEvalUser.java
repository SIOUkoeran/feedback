package com.seoul.feedback.dto.response.feedback;

import com.seoul.feedback.dto.response.UserResponse;
import com.seoul.feedback.entity.Feedback;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedbackEvalUser {

    private Long projectId;
    private Long feedbackId;
    private UserResponse appraisedUser;
    private String message;
    private int star;

    @Builder
    public FeedbackEvalUser(Feedback feedback){
        this.projectId = feedback.getProject().getId();
        this.feedbackId = feedback.getId();
        this.appraisedUser = UserResponse.builder().user(feedback.getAppraisedUser()).build();
        this.message = feedback.getMessage();
        this.star = feedback.getStar();
    }
}
