package com.seoul.feedback.dto.response;

import com.seoul.feedback.entity.Feedback;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SaveFeedbackResponse {

    private Long evalUserId;
    private String evalUserLogin;
    private Long appraisedUserId;
    private String appraisedUserLogin;
    private String message;
    private Long projectId;

    @Builder
    public SaveFeedbackResponse(Feedback feedback){

        this.evalUserId = feedback.getEvalUser().getId();
        this.evalUserLogin = feedback.getEvalUser().getLogin();
        this.appraisedUserId = feedback.getAppraisedUser().getId();
        this.appraisedUserLogin = feedback.getAppraisedUser().getLogin();
        this.message = feedback.getMessage();
        this.projectId = feedback.getProject().getId();
    }
}
