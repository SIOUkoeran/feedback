package com.seoul.feedback.service.feedback;

import com.seoul.feedback.entity.User;
import com.seoul.feedback.exception.EntityNotFoundException;
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
}
