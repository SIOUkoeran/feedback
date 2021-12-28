package com.seoul.feedback.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@RestControllerAdvice
public class FeedbackExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * In createFeedbackRequest, equal evalUserId = appraisedUserId
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {CreateFeedbackUserIdDuplicateException.class})
    public ResponseEntity<Object> CreateFeedbackUserIdDuplicateException(CreateFeedbackUserIdDuplicateException ex, WebRequest request){
        ErrorResponse errorResponse= new ErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

    }


}
