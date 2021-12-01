package com.seoul.feedback.controller;

import com.seoul.feedback.common.BaseControllerTest;
import com.seoul.feedback.dto.request.FeedbackCreateRequest;
import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.ProjectUpdateRequest;
import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.entity.Feedback;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.repository.FeedbackRepository;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.RegisterRepository;
import com.seoul.feedback.repository.UserRepository;
import com.seoul.feedback.service.FeedbackService;
import com.seoul.feedback.service.ProjectService;
import com.seoul.feedback.service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProjectService projectService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    RegisterService registerService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    RegisterRepository registerRepository;

    @Test
    void getUserListByProjectId() throws Exception {
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
        ProjectUpdateRequest projectUpdateRequest = new ProjectUpdateRequest("testProject", "testProjectName", Arrays.asList(userCreateRequest1, userCreateRequest, userCreateRequest2));
        ProjectCreateRequest projectCreateRequest = new ProjectCreateRequest("testProject", "testProject",Arrays.asList(userCreateRequest1, userCreateRequest, userCreateRequest2));
        Project project1 = this.projectService.save(projectCreateRequest);
        Project savedProject = this.projectService.update(project1.getId(),projectUpdateRequest);
        this.registerService.saveAll(savedProject.getId(), Arrays.asList(userCreateRequest1, userCreateRequest, userCreateRequest2));
        Feedback saveFeedback = this.feedbackService.saveFeedback(new FeedbackCreateRequest(user1.getId(), user2.getId(), "this is feedback comment", 5), 1L);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/project/{projectId}/user/{userId}/feedback-list", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("[0].userId").exists())
//                .andExpect(jsonPath("[0].login").exists())
//                .andExpect(jsonPath("[0].feedback").isBoolean())
//                .andDo(document("getUserFeedbackListByProjectId",
//                        requestHeaders(
//                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 컨텐트 타입")
//                        ),
//                        pathParameters(
//                                parameterWithName("projectId").description("요청 프로젝트 ID"),
//                                parameterWithName("userId").description("요청 유저 ID")
//                        ),
//                        responseHeaders(
//                                headerWithName(HttpHeaders.CONTENT_TYPE).description("결과 컨텐트 타입")
//                        ),
//                        relaxedResponseFields(
//                                fieldWithPath("[0].userId").description("결과 유저 ID"),
//                                fieldWithPath("[0].login").description("결과 유저 닉네임"),
//                                fieldWithPath("[0].feedback").description("True : 유저에게 피드백을 남김, False : 피드백 남기지 않음.")
//                        ))
//                );

    }
}
