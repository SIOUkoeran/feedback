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
    protected ResponseEntity handleUserDuplicatedException(final UserDuplicatedException e) {
        System.out.println("handleUserDuplicatedException");
        System.out.println("이미 등록된 유저입니다");
        ErrorResponse errorResponse = new ErrorResponse(e, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<?> handleEntityNotFoundException(final EntityNotFoundException e) {
        System.out.println("handleEntityNotFoundException");
        ErrorResponse errorResponse= new ErrorResponse(e, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
