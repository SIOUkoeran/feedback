package com.seoul.feedback.controller;

import com.seoul.feedback.dto.request.FeedbackCreateRequest;
import com.seoul.feedback.dto.response.feedback.FeedbackResponse;
import com.seoul.feedback.exception.CreateFeedbackUserIdDuplicateException;
import com.seoul.feedback.service.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/v1/", produces = "application/json; charset=UTF-8")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final HttpSession session;


    @PostMapping("/project/{projectId}/feedback")
    public ResponseEntity createFeedback(@PathVariable(name = "projectId") Long projectId, @RequestBody @Valid FeedbackCreateRequest feedbackCreateRequest) {

        if (feedbackCreateRequest.getAppraisedUserId() == feedbackCreateRequest.getEvalUserId()){
            throw new CreateFeedbackUserIdDuplicateException("You cannot evaluate yourself.");
        }

        return new ResponseEntity(FeedbackResponse.builder()
                .feedback(this.feedbackService.saveFeedback(feedbackCreateRequest, projectId))
                .projectId(projectId)
                .build(), HttpStatus.CREATED);

    }

    @GetMapping("/feedback/{feedbackId}")
    public ResponseEntity getFeedback(@PathVariable(name = "feedbackId") Long feedbackId) {

        FeedbackResponse feedbackResponse = this.feedbackService.getOneFeedback(feedbackId);

        return ResponseEntity.ok().body(feedbackResponse);
    }

    @GetMapping("/project/{projectId}/feedbacks")
    public ResponseEntity getFeedbackListByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok().body(this.feedbackService.findFeedbackList(projectId));
    }

    @GetMapping("/user/{userId}/evalFeedbacks")
    public ResponseEntity findFeedbacksByEvalId(@PathVariable(name = "userId") Long userId) {

        return ResponseEntity.ok().body(this.feedbackService.feedbackEvalList(userId));
    }

    @GetMapping("/user/{userId}/appraisedFeedbacks")
    public ResponseEntity findFeedbacksByAppraisedId(@PathVariable(name = "userId") Long userId) {

        return ResponseEntity.ok().body(this.feedbackService.feedbackAppraisedList(userId));
    }



}
