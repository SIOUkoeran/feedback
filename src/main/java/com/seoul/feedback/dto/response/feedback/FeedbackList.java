package com.seoul.feedback.dto.response.feedback;

import com.seoul.feedback.dto.response.UserResponse;
import com.seoul.feedback.entity.Feedback;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedbackList {

    private Long feedbackId;
    private UserResponse evalUser;
    private UserResponse appraisedUser;
    private String message;
    private int star;


    @Builder
    public FeedbackList(Feedback feedback)
    {
        this.feedbackId = feedback.getId();
        this.evalUser = UserResponse.builder().user(feedback.getEvalUser()).build();
        this.appraisedUser = UserResponse .builder().user(feedback.getAppraisedUser()).build();
        this.message = feedback.getMessage();
        this.star = feedback.getStar();
    }
}
