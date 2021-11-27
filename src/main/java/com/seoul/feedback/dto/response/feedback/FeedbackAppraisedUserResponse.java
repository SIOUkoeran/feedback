package com.seoul.feedback.dto.response.feedback;

import com.seoul.feedback.entity.enums.FeedbackStatus;
import com.seoul.feedback.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FeedbackAppraisedUserResponse {

    private Long appraisedUserId;
    private String appraisedUserLogin;
    private List<FeedbackAppraisedUser> appraiseFeedbackList;

    @Builder
    public FeedbackAppraisedUserResponse(User user){
        this.appraisedUserId = user.getId();
        this.appraisedUserLogin = user.getLogin();
        this.appraiseFeedbackList = user.getReceivedFeedback().stream()
                .filter(feedback ->  feedback.getFeedbackStatus() == FeedbackStatus.REGISTER)
                .map(feedback -> FeedbackAppraisedUser.builder()
                        .feedback(feedback).build())
                .collect(Collectors.toList());
    }

}
