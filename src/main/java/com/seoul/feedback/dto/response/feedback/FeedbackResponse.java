package com.seoul.feedback.dto.response.feedback;

import com.seoul.feedback.dto.response.UserResponse;
import com.seoul.feedback.entity.Feedback;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedbackResponse {

    private Long projectId;
    private Long feedbackId;
    private UserResponse evalUser;
    private UserResponse appraisedUser;
    private String message;
    private int star;
    private LocalDateTime createdAt;

    @Builder
    public FeedbackResponse(Long projectId, Feedback feedback)
    {
        this.projectId = projectId;
        this.feedbackId = feedback.getId();
        this.evalUser = UserResponse.builder().user(feedback.getEvalUser()).build();
        this.appraisedUser = UserResponse .builder().user(feedback.getAppraisedUser()).build();
        this.message = feedback.getMessage();
        this.createdAt = feedback.getCreatedAt();
        this.star = feedback.getStar();
    }

    @Override
    public String toString() {
        return "FeedbackResponse{" +
                "projectId=" + projectId +
                ", feedbackId=" + feedbackId +
                ", evalUser=" + evalUser +
                ", appraisedUser=" + appraisedUser +
                ", message='" + message + '\'' +
                ", star=" + star +
                ", createdAt=" + createdAt +
                '}';
    }
}
