package com.seoul.feedback.dto.response.feedback;

import com.seoul.feedback.dto.response.UserResponse;
import com.seoul.feedback.entity.Feedback;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedbackAppraisedUser {

    private Long projectId;
    private Long feedbackId;
    private UserResponse evalUser;
    private String message;
    private int star;

    @Builder
    public FeedbackAppraisedUser(Feedback feedback){
        this.projectId = feedback.getProject().getId();
        this.feedbackId = feedback.getId();
        this.evalUser = UserResponse.builder().user(feedback.getEvalUser()).build();
        this.message = feedback.getMessage();
        this.star = feedback.getStar();
    }
}
