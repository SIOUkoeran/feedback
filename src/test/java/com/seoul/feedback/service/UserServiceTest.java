package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.FeedbackCreateRequest;
import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.ProjectUpdateRequest;
import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.dto.response.UserResponse;
import com.seoul.feedback.entity.Feedback;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.repository.FeedbackRepository;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.RegisterRepository;
import com.seoul.feedback.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    RegisterService registerService;
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    RegisterRepository registerRepository;
    @BeforeEach
    void setUp(){
        this.userRepository.deleteAll();
        this.projectRepository.deleteAll();
        this.feedbackRepository.deleteAll();
        this.registerRepository.deleteAll();
    }
    @Test
    @Transactional
    @Rollback(value = false)
    void getUserListByProjectId(){

        User user1 = User.builder()
                .login("testUser1")
                .build();

        User user2 = User.builder()
                .login("testUser2")
                .build();

        User user3 = User.builder()
                .login("testUser3")
                .build();
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        this.userRepository.save(user3);

        Project project = Project.builder()
                .description("testProject")
                .name("testProjectName")
                .build();
        UserCreateRequest userCreateRequest = new UserCreateRequest(user1.getLogin());
        UserCreateRequest userCreateRequest1 = new UserCreateRequest(user2.getLogin());
        UserCreateRequest userCreateRequest2 = new UserCreateRequest(user3.getLogin());
        ProjectUpdateRequest projectUpdateRequest = new ProjectUpdateRequest("testProject", "testProjectName",Arrays.asList(userCreateRequest1, userCreateRequest, userCreateRequest2));
        ProjectCreateRequest projectCreateRequest = new ProjectCreateRequest("testProject", "testProject",Arrays.asList(userCreateRequest1, userCreateRequest, userCreateRequest2));
        Project project1 = this.projectService.save(projectCreateRequest);
        Project savedProject = this.projectService.update(project1.getId(),projectUpdateRequest);
        this.registerService.saveAll(savedProject.getId(), Arrays.asList(userCreateRequest1, userCreateRequest, userCreateRequest2));
        Feedback saveFeedback = this.feedbackService.saveFeedback(new FeedbackCreateRequest(user1.getId(), user2.getId(), "this is feedback comment", 5), 1L);
        List<UserResponse.Project> userListByProjectId = this.userService.getUserListByProjectId(project1.getId(), user1.getId());
        UserResponse.Project project2 = userListByProjectId.get(0);
        assertThat(userListByProjectId.get(0).isFeedback()).isTrue();
        assertThat(project2.getLogin()).isEqualTo("testUser2");
        assertThat(project2.getUserId()).isEqualTo(2L);
        System.out.println("userListByProjectId.size() = " + userListByProjectId.size());
    }
}
