package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.FeedbackCreateRequest;
import com.seoul.feedback.dto.response.feedback.FeedbackProjectIdResponse;
import com.seoul.feedback.entity.Feedback;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.repository.FeedbackRepository;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class FeedbackServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    ProjectRepository projectRepository;

    @BeforeEach
    void setUp(){
        this.projectRepository.deleteAll();
        this.feedbackRepository.deleteAll();
        this.userRepository.deleteAll();
    }

    @Test
    @Transactional
    void saveFeedback(){
        User eval = User.builder()
                .login("evalUser").build();
        User appraise = User.builder()
                .login("appraisedUser").build();
        User evalUser = this.userRepository.save(eval);
        User appraisedUser = this.userRepository.save(appraise);
        String message = "열정적인 리뷰 감사합니다!.";
        Project project = Project.builder()
                .name("testProject")
                .description("testDescription")
                .build();
        Project project1 = this.projectRepository.save(project);

        Feedback saveFeedback = this.feedbackService.saveFeedback(new FeedbackCreateRequest(evalUser.getId(), appraisedUser.getId(), message, 5), project.getId());

        assertThat(saveFeedback.getEvalUser().getId()).isEqualTo(evalUser.getId());
        assertThat(saveFeedback.getAppraisedUser().getId()).isEqualTo(appraisedUser.getId());
        assertThat(evalUser.getGaveFeedback().get(0)).isNotNull();
        assertThat(evalUser.getReceivedFeedback().size()).isEqualTo(0);
        assertThat(evalUser.getGaveFeedback().get(0).getAppraisedUser().getLogin())
                                                            .isEqualTo(appraisedUser.getLogin());
        assertThat(appraisedUser.getReceivedFeedback().get(0)).isNotNull();
        assertThat(appraisedUser.getGaveFeedback().size()).isEqualTo(0);
        assertThat(appraisedUser.getReceivedFeedback().get(0).getEvalUser().getLogin())
                .isEqualTo(evalUser.getLogin());

        assertThat(saveFeedback.getProject().getId()).isEqualTo(project1.getId());
    }

    @Test
    @Transactional
    void feedbackList(){
        Project project = Project.builder()
                .name("testProject")
                .description("projectDescription")
                .build();
        this.projectRepository.save(project);
        User evalUser = User.builder()
                .login("eval")
                .build();
        this.userRepository.save(evalUser);
        String message = "열정적인 리뷰 감사합니다!.길이맞추기용용용용용용용용용용";

        for (int i = 0; i < 10; i++) {
             User appraisedUser = User.builder()
                         .login("appraise" + i)
                         .build();

            this.userRepository.save(appraisedUser);
            this.feedbackService.saveFeedback(new FeedbackCreateRequest(evalUser.getId(), appraisedUser.getId(),message + i, 5), project.getId());
        }
        FeedbackProjectIdResponse feedbackList = this.feedbackService.findFeedbackList(project.getId());
        assertThat(feedbackList.getFeedbackResponses().size()).isEqualTo(10);


        assertThat(feedbackList.getFeedbackResponses().get(3).getEvalUser().getUserId()).isEqualTo(evalUser.getId());

//        for (int i = 0; i < 10; i++) {
//            assertThat(feedbackList.getFeedbackResponses().get(i).getAppraisedUser().getUserId()).isEqualTo(Long.valueOf(i + 2));
//        }

    }

}