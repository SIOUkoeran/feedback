package com.seoul.feedback.exception;



import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
@AllArgsConstructor
public class CreateFeedbackUserIdDuplicateException extends RuntimeException{
    public CreateFeedbackUserIdDuplicateException(String message) {
        super(message);
    }

    public CreateFeedbackUserIdDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
