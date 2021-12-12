package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.FeedbackCreateRequest;
import com.seoul.feedback.dto.response.feedback.FeedbackAppraisedUserResponse;
import com.seoul.feedback.dto.response.feedback.FeedbackEvalUserResponse;
import com.seoul.feedback.dto.response.feedback.FeedbackProjectIdResponse;
import com.seoul.feedback.dto.response.feedback.FeedbackResponse;
import com.seoul.feedback.entity.Feedback;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.exception.UserDuplicatedException;
import com.seoul.feedback.repository.FeedbackRepository;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Feedback saveFeedback(FeedbackCreateRequest request, Long projectId){

        Optional<User> evalUser = this.userRepository.findById(request.getEvalUserId());
        Optional<User> appraisedUser = this.userRepository.findById(request.getAppraisedUserId());
        Optional<Project> project = this.projectRepository.findById(projectId);

        throwEmpty(evalUser.isEmpty() || appraisedUser.isEmpty(), "User not Found");
        throwEmpty(project.isEmpty(), "Project not Found");

        return this.feedbackRepository.save(Feedback.createFeedback(evalUser.get(), appraisedUser.get(),
                                                            request.getMessage(), request.getStar(), project.get()));


    }

    @Transactional(readOnly = true)
    public FeedbackProjectIdResponse findFeedbackList(Long projectId)
    {
        Optional<Project> project = this.projectRepository.findById(projectId);
        throwEmpty(project.isEmpty(), "Project not Found");
        return FeedbackProjectIdResponse.builder()
                .project(project.get()).build();
    }

    @Transactional(readOnly = true)
    public FeedbackResponse getOneFeedback(Long feedbackId){
        Optional<Feedback> feedback = this.feedbackRepository.findById(feedbackId);
        throwEmpty(feedback.isEmpty(),"Feedback Not Found");
        return FeedbackResponse.builder()
                .feedback(feedback.get())
                .build();
    }

    @Transactional(readOnly = true)
    public FeedbackEvalUserResponse feedbackEvalList(Long evalUser) {
        Optional<User> user = this.userRepository.findById(evalUser);
        throwEmpty(user.isEmpty(), "User not Found");

        return FeedbackEvalUserResponse.builder().user(user.get()).build();
    }

    @Transactional(readOnly = true)
    public FeedbackAppraisedUserResponse feedbackAppraisedList(Long appraisedUser){
        Optional<User> user = this.userRepository.findById(appraisedUser);
        throwEmpty(user.isEmpty(), "User not Found");

        return FeedbackAppraisedUserResponse.builder().user(user.get()).build();
    }

    @Transactional
    public void deleteFeedback(Long feedbackId){
        Optional<Feedback> feedback= this.feedbackRepository.findById(feedbackId);
        throwEmpty(feedback.isEmpty(), "Feedback not Found");
        feedback.get().cancel();
    }


    private void throwEmpty(boolean empty, String s) {
        if (empty) {
            throw new EntityNotFoundException(s);
        }
    }
}
