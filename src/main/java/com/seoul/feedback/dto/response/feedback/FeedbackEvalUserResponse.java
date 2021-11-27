package com.seoul.feedback.dto.response.feedback;

import com.seoul.feedback.entity.enums.FeedbackStatus;
import com.seoul.feedback.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FeedbackEvalUserResponse {

    private Long EvalUserId;
    private String EvalUserLogin;
    private List<FeedbackEvalUser> evalFeedbackList;

    @Builder
    public FeedbackEvalUserResponse(User user){

        this.EvalUserId = user.getId();
        this.EvalUserLogin = user.getLogin();
        this.evalFeedbackList = user.getGaveFeedback().stream()
                .filter(feedback -> feedback.getFeedbackStatus() == FeedbackStatus.REGISTER)
                .map(feedback -> FeedbackEvalUser.builder()
                        .feedback(feedback).build())
                .collect(Collectors.toList());
    }
}
