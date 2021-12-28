package com.seoul.feedback.service.feedback;

import com.seoul.feedback.entity.User;
import com.seoul.feedback.service.validator.ServiceValidator;
import org.springframework.stereotype.Component;

@Component
public interface FeedbackValidator extends ServiceValidator {
    void isAppraisedUserRegisterProjectThrow(Long projectId, User appraisedUser);
    void isSameAppraisedUserAndEvalUser(Long evalUserId, Long appraisedUserId);
}
