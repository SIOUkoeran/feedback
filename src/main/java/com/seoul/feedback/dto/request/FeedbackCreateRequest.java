package com.seoul.feedback.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackCreateRequest {

    @NotNull
    private Long evalUserId;
    @NotNull
    private Long appraisedUserId;
    @NotNull
    private String message;

    @NotNull
    @Range(min = 1, max = 5)
    private int star;

    public FeedbackCreateRequest(Long evalUserId, Long appraisedUserId, String message, int star) {
        this.evalUserId = evalUserId;
        this.appraisedUserId = appraisedUserId;
        this.message = message;
        this.star = star;
    }
}
