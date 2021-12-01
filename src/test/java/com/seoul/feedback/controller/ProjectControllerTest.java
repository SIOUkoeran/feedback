package com.seoul.feedback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoul.feedback.common.BaseControllerTest;
import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.UserRepository;
import com.seoul.feedback.service.FeedbackService;
import com.seoul.feedback.service.ProjectService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProjectControllerTest extends BaseControllerTest {

    @Autowired
    MockMvc mockMvc;
//
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProjectService projectService;


    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    FeedbackService feedbackService;

    @Autowired
    UserRepository userRepository;


    @Test
    void getProjectListByUserId() throws Exception{
        User testUser = User.builder()
                .login("getProject")
                .build();
        this.userRepository.save(testUser);
        UserCreateRequest userCreateRequest = new UserCreateRequest(testUser.getLogin());

        ProjectCreateRequest projectCreateRequest = new ProjectCreateRequest("Project1", "projectDescription", Arrays.asList(userCreateRequest));
        ProjectCreateRequest projectCreateRequest1 = new ProjectCreateRequest("project2", "projectDescription", Arrays.asList(userCreateRequest));
        mockMvc.perform(post("/api/v1/project/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(projectCreateRequest)))
                .andDo(print());

        mockMvc.perform(post("/api/v1/project/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(projectCreateRequest1)))
                .andDo(print());

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/project/user/{userId}", testUser.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].projectId").exists())
                .andExpect(jsonPath("[1].projectId").exists())
                .andDo(document("getProjectListByUserId",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        pathParameters(
                                parameterWithName("userId").description("유저 아이디")
                        ),
                        responseFields(
                                fieldWithPath("[0].projectId").description("프로젝트 아이디"),
                                fieldWithPath("[0].name").description("프로젝트 이름"),
                                fieldWithPath("[0].description").description("프로젝트 내용"),
                                fieldWithPath("[1].projectId").description("프로젝트 아이디"),
                                fieldWithPath("[1].name").description("프로젝트 이름"),
                                fieldWithPath("[1].description").description("프로젝트 내용")
                        )
                ));

    }

    @Test
    void getProjectListByNoRegistered() throws Exception {

        User testUser = User.builder()
                .login("testUser")
                .build();

        this.userRepository.save(testUser);
        mockMvc.perform(get("/api/v1/project/user/{userId}", 2L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
//
//    @Mock
//    ProjectService projectService;
//
//    @InjectMocks
//    ProjectController projectController;
//
//    @BeforeAll
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
//    }
//
//    @Test
//    @DisplayName("프로젝트 post 요청")
//    public void 프로젝트_post() throws Exception {
//
//        mockMvc.perform(post("/api/v1/project")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(new ProjectCreateRequest("project100", "desc100"))))
//                .andExpect(MockMvcResultMatchers.content().string("project100, desc100"))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }
}