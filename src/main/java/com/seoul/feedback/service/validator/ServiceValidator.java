package com.seoul.feedback.service.validator;


import javax.persistence.EntityNotFoundException;


public interface ServiceValidator {

    default void throwEmpty(boolean empty, String message){
        if (empty){
            throw new EntityNotFoundException(message);
        }
    }

}
