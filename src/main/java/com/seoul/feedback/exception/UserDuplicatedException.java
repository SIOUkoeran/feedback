package com.seoul.feedback.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UserDuplicatedException extends RuntimeException {

    public UserDuplicatedException(String message) {
        super(message);
    }
}
