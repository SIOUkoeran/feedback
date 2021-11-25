package com.seoul.feedback.controller;

import com.seoul.feedback.dto.request.FeedbackCreateRequest;
import com.seoul.feedback.dto.response.feedback.FeedbackResponse;
import com.seoul.feedback.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedbackController {

    private final FeedbackService feedbackService;


    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @PostMapping("/project/{projectId}/feedback")
    public ResponseEntity createFeedback(@PathVariable(name = "projectId") Long projectId, @RequestBody @Valid FeedbackCreateRequest feedbackCreateRequest)
    {

            return new ResponseEntity(FeedbackResponse.builder()
                                        .feedback(this.feedbackService.saveFeedback(feedbackCreateRequest, projectId))
                                        .projectId(projectId)
                                        .build(), HttpStatus.CREATED);
    }

    @GetMapping("/feedback/{feedbackId}")
    public ResponseEntity getFeedback(@PathVariable(name = "feedbackId") Long feedbackId){

        FeedbackResponse feedbackResponse = this.feedbackService.getOneFeedback(feedbackId);

        return  ResponseEntity.ok().body(feedbackResponse);
    }

    @GetMapping("/project/{projectId}/feedbacks")
    public ResponseEntity getFeedbackListByProject(@PathVariable Long projectId){
        return ResponseEntity.ok().body(this.feedbackService.findFeedbackList(projectId));
    }



    @GetMapping("/user/{userId}/evalFeedbacks")
    public ResponseEntity findFeedbacksByEvalId(@PathVariable(name = "userId") Long userId){

        return ResponseEntity.ok().body(this.feedbackService.feedbackEvalList(userId));
    }

    @GetMapping("/user/{userId}/appraisedFeedbacks")
    public ResponseEntity findFeedbacksByAppraisedId(@PathVariable(name ="userId") Long userId){

        return ResponseEntity.ok().body(this.feedbackService.feedbackAppraisedList(userId));
    }



}
