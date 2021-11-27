package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Feedback;
import com.seoul.feedback.entity.enums.FeedbackStatus;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.seoul.feedback.entity.Feedback.createFeedback;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FeedbackRepositoryTest {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;
    @Test
    void 피드백_생성(){
        User toUser = User.builder()
                .login("test1")
                .build();

        User fromUser = User.builder()
                .login("test2")
                .build();

        Project project = Project.builder()
                .name("projectTest")
                .description("testDescription")
                .build();

        User toUser1 = this.userRepository.save(toUser);
        User fromUser1 = this.userRepository.save(fromUser);
        Project project1 = this.projectRepository.save(project);

        String message = "message";
        int star = 1;

        Feedback feedback = createFeedback(toUser1, fromUser1, message, star, project1);
        Feedback feedback1 = createFeedback(toUser1, fromUser1, message, star + 1, project1);

        assertThat(feedback.getMessage()).isEqualTo("message");
        assertThat(feedback.getStar()).isEqualTo(1);
        assertThat(feedback.getFeedbackStatus()).isEqualTo(FeedbackStatus.REGISTER);
        assertThat(feedback.getEvalUser().getLogin()).isEqualTo("test1");

    }

    @Test
    @Transactional
    void 피드백_생성_유저피드백목록()
    {
        User toUser = User.builder()
                .login("test1")
                .build();

        User fromUser = User.builder()
                .login("test2")
                .build();

        Project project = Project.builder()
                .name("projectTest")
                .description("testDescription")
                .build();

        User toUser1 = this.userRepository.save(toUser);
        User fromUser1 = this.userRepository.save(fromUser);
        Project project1 = this.projectRepository.save(project);
        Feedback feedback = createFeedback(toUser1, fromUser1, "FeedbackTest", 1, project1);

        assertThat(toUser.getGaveFeedback().get(0).getMessage()).isEqualTo("FeedbackTest");
        assertThat(toUser.getGaveFeedback().get(0).getEvalUser().getLogin()).isEqualTo("test1");
        assertThat(toUser.getGaveFeedback().get(0).getAppraisedUser().getLogin()).isEqualTo("test2");
    }

    @Test
    @Transactional
    void 피드백_삭제()
    {
        User toUser = User.builder()
                .login("test1")
                .build();

        User fromUser = User.builder()
                .login("test2")
                .build();

        Project project = Project.builder()
                .name("projectTest")
                .description("testDescription")
                .build();

        User toUser1 = this.userRepository.save(toUser);
        User fromUser1 = this.userRepository.save(fromUser);
        Project project1 = this.projectRepository.save(project);
        Feedback feedback = createFeedback(toUser1, fromUser1, "FeedbackTest", 1, project1);
        this.feedbackRepository.save(feedback);
        assertThat(feedback.getFeedbackStatus()).isEqualTo(FeedbackStatus.REGISTER);
        feedback.cancel();
        assertThat(feedback.getFeedbackStatus()).isEqualTo(FeedbackStatus.CANCEL);
    }

}