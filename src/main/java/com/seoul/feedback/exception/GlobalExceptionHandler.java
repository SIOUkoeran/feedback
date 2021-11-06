package com.seoul.feedback.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserDuplicatedException.class)
    protected void handleUserDuplicatedException(final UserDuplicatedException e) {
        System.out.println("handleUserDuplicatedException");
        System.out.println("이미 등록된 유저입니다");
    }
}
