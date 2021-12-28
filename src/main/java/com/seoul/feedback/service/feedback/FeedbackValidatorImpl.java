package com.seoul.feedback.service.feedback;

import com.seoul.feedback.entity.User;
import com.seoul.feedback.exception.EntityNotFoundException;
import com.seoul.feedback.exception.UserDuplicatedException;
import org.springframework.stereotype.Component;

@Component
public class FeedbackValidatorImpl implements FeedbackValidator{

    @Override
    public void isAppraisedUserRegisterProjectThrow(Long projectId, User appraisedUser) {
        boolean isAppraisedUserRegisterProject = appraisedUser.getRegisterList().stream().filter(
                        register -> register.getProject().getId() == projectId)
                .findAny().isEmpty();
        if (isAppraisedUserRegisterProject){
            throw new EntityNotFoundException("AppraisedUser is not registered Project");
        }
    }

    @Override
    public void isSameAppraisedUserAndEvalUser(Long evalUserId, Long appraisedUserId) {
        if (evalUserId == appraisedUserId)
            throw new UserDuplicatedException("AppraisedUser and EvalUser must not be same");
    }
}
