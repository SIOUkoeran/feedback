package com.seoul.feedback.dto.response.feedback;

import com.seoul.feedback.entity.enums.FeedbackStatus;
import com.seoul.feedback.entity.Project;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FeedbackProjectIdResponse {

    private Long projectId;
    private List<FeedbackList> feedbackResponses;

    @Builder
    public FeedbackProjectIdResponse(Project project){
        this.projectId = project.getId();
        this.feedbackResponses = project.getFeedbackList().stream()
                .filter(feedback -> feedback.getFeedbackStatus() == FeedbackStatus.REGISTER)
                .map(feedback -> FeedbackList.builder()
                        .feedback(feedback).build())
                .collect(Collectors.toList());
    }
}
