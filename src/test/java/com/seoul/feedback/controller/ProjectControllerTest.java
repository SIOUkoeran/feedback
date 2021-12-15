package com.seoul.feedback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoul.feedback.common.BaseControllerTest;
import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.ProjectUpdateRequest;
import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.entity.enums.Role;
import com.seoul.feedback.repository.FeedbackRepository;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.RegisterRepository;
import com.seoul.feedback.repository.UserRepository;
import com.seoul.feedback.service.feedback.FeedbackService;
import com.seoul.feedback.service.ProjectService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    RegisterRepository registerRepository;



    @Test
    @WithMockUser(roles = {"STUDENT"})
    void getProjectListByUserId() throws Exception{
        User testUser = User.builder()
                .login("getProject")
                .role(Role.STUDENT)
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
    @WithMockUser(roles = {"STUDENT"})
    void getProjectListByNoRegistered() throws Exception {

        User testUser = User.builder()
                .role(Role.STUDENT)
                .login("testUser")
                .build();

        this.userRepository.save(testUser);
        mockMvc.perform(get("/api/v1/project/user/{userId}", 10L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("DB에 없는 유저들 생성 후 프로젝트에 등록")
    @WithMockUser(roles = {"STUDENT"})
    void registCardetToProject() throws Exception {

        UserCreateRequest userCreateRequest = new UserCreateRequest("mkim3");
        UserCreateRequest userCreateRequest1 = new UserCreateRequest("mkim2");
        ProjectUpdateRequest projectUpdateRequest = new ProjectUpdateRequest("projectName", "프로젝트 한글 잘 나오나요?", Arrays.asList(userCreateRequest, userCreateRequest1));
        Project project = new Project("project1", "projectDescription");
        this.projectRepository.save(project);
        
        mockMvc.perform(put("/api/v1/project/{projectId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(projectUpdateRequest)))
                .andDo(print());

        List<User> all = userRepository.findAll();
        for (User user : all) {
            System.out.println("user.getLogin() = " + user.getLogin());
        }
    }

}