package com.seoul.feedback.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.swing.text.html.parser.Entity;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserDuplicatedException.class)
    protected void handleUserDuplicatedException(final UserDuplicatedException e) {
        System.out.println("handleUserDuplicatedException");
        System.out.println("이미 등록된 유저입니다");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<?> handleEntityNotFoundException(final EntityNotFoundException e) {
        System.out.println("handleEntityNotFoundException");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


}
