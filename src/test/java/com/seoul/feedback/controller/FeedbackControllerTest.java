package com.seoul.feedback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoul.feedback.common.BaseControllerTest;
import com.seoul.feedback.dto.request.FeedbackCreateRequest;
import com.seoul.feedback.dto.response.feedback.FeedbackResponse;
import com.seoul.feedback.entity.enums.Role;
import com.seoul.feedback.exception.CreateFeedbackUserIdDuplicateException;
import com.seoul.feedback.repository.RegisterRepository;

import com.seoul.feedback.security.SessionUser;
import com.seoul.feedback.service.feedback.FeedbackService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.seoul.feedback.entity.Feedback;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.repository.FeedbackRepository;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class FeedbackControllerTest extends BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    RegisterRepository registerRepository;
    @Autowired
    FeedbackService feedbackService;

    @Test
    @WithMockUser(roles = {"STUDENT"})
    public void getFeedbackList() throws Exception{
        User evalUser = User.builder()
                .login("evalTest")
                .role(Role.STUDENT)
                .build();
        this.userRepository.save(evalUser);
        User appraisedUser = User.builder()
                .login("appraiseTest")
                .role(Role.STUDENT)
                .build();
        this.userRepository.save(appraisedUser);
        Project project = Project.builder()
                .description("testProject")
                .name("project")
                .build();
        this.projectRepository.save(project);
        Feedback.createFeedback(evalUser, appraisedUser, "?????????", 5, project);
        Feedback feedback = this.feedbackRepository.save(Feedback.createFeedback(evalUser, appraisedUser, "?????????", 5, project));
        mockMvc.perform(get("/api/v1/project/{projectId}/feedbacks", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("feedbackListByProject",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("projectId").description("???????????? ?????????"),
                                fieldWithPath("feedbackResponses[0].feedbackId").description("????????? ?????????"),
                                fieldWithPath("feedbackResponses[0].evalUser.userId").description("?????? ?????? ?????????"),
                                fieldWithPath("feedbackResponses[0].evalUser.login").description("?????? ?????? ?????????"),
                                fieldWithPath("feedbackResponses[0].appraisedUser.userId").description("????????? ?????? ?????????"),
                                fieldWithPath("feedbackResponses[0].appraisedUser.login").description("????????? ?????? ?????????"),
                                fieldWithPath("feedbackResponses[0].message").description("?????? ?????????"),
                                fieldWithPath("feedbackResponses[0].star").description("?????? ??????")
                        )
                ))
        ;

    }

    @Test
    @WithMockUser(roles = {"STUDENT"})
    void getEvalUserFeedback() throws Exception{
        User evalUser = User.builder()
                .login("evalTest")
                .role(Role.STUDENT)
                .build();
        this.userRepository.save(evalUser);
        User appraisedUser = User.builder()
                .login("appraiseTest")
                .role(Role.STUDENT)
                .build();
        this.userRepository.save(appraisedUser);
        Project project = Project.builder()
                .description("testProject")
                .name("project")
                .build();
        this.projectRepository.save(project);

        this.feedbackRepository.save(Feedback.createFeedback(evalUser, appraisedUser, "?????????", 5, project));
        mockMvc.perform(get("/api/v1/user/{userId}/evalFeedbacks", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("evalFeedbacks",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("evalFeedbackList[0].projectId").description("???????????? ?????????"),
                                fieldWithPath("evalFeedbackList[0].feedbackId").description("???????????? ?????????"),
                                fieldWithPath("evalUserId").description("????????? ?????????"),
                                fieldWithPath("evalUserLogin").description(" ????????? ?????????"),
                                fieldWithPath("evalFeedbackList[0].appraisedUser.userId").description("????????? ?????????"),
                                fieldWithPath("evalFeedbackList[0].appraisedUser.login").description("????????? ?????????"),
                                fieldWithPath("evalFeedbackList[0].message").description("???????????? ?????????"),
                                fieldWithPath("evalFeedbackList[0].star").description("???????????? ?????? ??????")
                        )
                ))
        ;

    }

    @Test
    @WithMockUser(roles = {"STUDENT"})
    void getAppraisedUserFeedbackList() throws Exception{
        User evalUser = User.builder()
                .login("evalTest")
                .role(Role.STUDENT)
                .build();
        this.userRepository.save(evalUser);
        System.out.println("evalUser.getId() = " + evalUser.getId());
        User appraisedUser = User.builder()
                .role(Role.STUDENT)
                .login("appraiseTest")
                .build();
        this.userRepository.save(appraisedUser);
        Project project = Project.builder()
                .description("testProject")
                .name("project")
                .build();
        this.projectRepository.save(project);

        this.feedbackRepository.save(Feedback.createFeedback(evalUser, appraisedUser, "?????????", 5, project));
        mockMvc.perform(get("/api/v1/user/{userId}/appraisedFeedbacks", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("appraisedUserId").exists())
                .andDo(document("appraisedFeedbacks",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("appraisedUserId").description("?????? ?????? ?????? ?????????"),
                                fieldWithPath("appraisedUserLogin").description("?????? ?????? ?????? ?????????"),
                                fieldWithPath("appraiseFeedbackList[0].projectId").description("???????????? ?????????"),
                                fieldWithPath("appraiseFeedbackList[0].feedbackId").description("???????????? ?????????"),
                                fieldWithPath("appraiseFeedbackList[0].evalUser.userId").description("????????? ?????? ?????????"),
                                fieldWithPath("appraiseFeedbackList[0].evalUser.login").description(" ????????? ?????? ?????????"),
                                fieldWithPath("appraiseFeedbackList[0].message").description("???????????? ?????????"),
                                fieldWithPath("appraiseFeedbackList[0].star").description("???????????? ?????? ??????")
                        )
                ))
        ;

    }

    @Test
    @WithMockUser(roles = {"STUDENT"})
    void createFeedback() throws Exception {
        User evalUser = User.builder()
                .login("evalTest")
                .role(Role.STUDENT)
                .build();
        this.userRepository.save(evalUser);

        User appraisedUser = User.builder()
                .login("appraiseTest")
                .role(Role.STUDENT)
                .build();
        this.userRepository.save(appraisedUser);
        Project project = Project.builder()
                .description("testProject")
                .name("project")
                .build();
        Project p = this.projectRepository.save(project);
        FeedbackCreateRequest feedbackCreateRequest = new
                FeedbackCreateRequest(evalUser.getId(), appraisedUser.getId(), "?????????", 3);
        mockMvc.perform(post("/api/v1/project/{projectId}/feedback", p.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(feedbackCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("createFeedback",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("request-header")
                        ),
                        requestFields(
                                fieldWithPath("evalUserId").description("?????? ?????? ?????????"),
                                fieldWithPath("appraisedUserId").description("????????? ?????? ?????????"),
                                fieldWithPath("message").description("?????? ?????????"),
                                fieldWithPath("star").description("?????? ?????????")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("response-header")
                        ),
                        responseFields(
                                fieldWithPath("projectId").description("???????????? ?????????"),
                                fieldWithPath("feedbackId").description("????????? ?????????"),
                                fieldWithPath("evalUser.userId").description("?????? ?????? ?????????"),
                                fieldWithPath("evalUser.login").description("?????? ?????? ?????????"),
                                fieldWithPath("appraisedUser.userId").description("????????? ?????? ?????????"),
                                fieldWithPath("appraisedUser.login").description("????????? ?????? ?????????"),
                                fieldWithPath("message").description("????????? ?????????"),
                                fieldWithPath("star").description("????????? ??????"),
                                fieldWithPath("createdAt").description("????????? ?????? ??????")
                        )
                        ))
        ;
    }

    @Test
    @DisplayName("????????? ????????? ?????? ???????????? ????????? ????????? ?????? ???")
    @WithMockUser(roles = {"STUDENT"})
    void createFeedbackWithSameUserId() throws Exception {

        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest(1L, 1L, "same userId", 5);
        mockMvc.perform(post("/api/v1/project/{projectId}/feedback",1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(feedbackCreateRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ????????? ????????? ????????????")
    @WithMockUser(roles = {"STUDENT"}, username = "evalUser")
    void findFeedback() throws Exception{
        User evalUser = saveUser("evalUser");
        User appraisedUser = saveUser("appraisedUser");
        Project savedProject = saveProject("testProject", "testProjectDescription");
        Feedback feedback  = saveFeedback(evalUser, appraisedUser, "testFeedback", 5, savedProject);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user",new SessionUser(evalUser));
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/project/{projectId}/feedback/user/{appraisedId}", 1L, 2L)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("projectId").exists())
                .andExpect(jsonPath("feedbackId").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("star").exists())
                .andDo(document("findFeedback",

                        requestHeaders(
                                headerWithName("Content-Type").description("content-type")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN).description("?????? origin"),
                                headerWithName(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS).description("?????? ?????????"),
                                headerWithName(HttpHeaders.ACCESS_CONTROL_MAX_AGE).description("?????? age"),
                                headerWithName(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS).description("?????? ??????"),
                                headerWithName(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS).description("?????? ?????? ??????"),
                                headerWithName(HttpHeaders.VARY).description(""),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("????????? ?????? ??????")
                        ),
                        pathParameters(
                                parameterWithName("projectId").description("???????????? ???????????? ????????? ???????????? ?????????"),
                                parameterWithName("appraisedId").description("???????????? ???????????? ????????? ?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("projectId").description("???????????? ???????????? ????????? ???????????? ?????????"),
                                fieldWithPath("feedbackId").description("???????????? ???????????? ?????????"),
                                fieldWithPath("message").description("???????????? ???????????? ????????? ???????????? ?????????"),
                                fieldWithPath("star").description("???????????? ???????????? ????????? ???????????? ??????"),
                                fieldWithPath("evalUser.userId").description("???????????? ???????????? ????????? ?????? ?????????"),
                                fieldWithPath("evalUser.login").description("???????????? ???????????? ????????? ?????? ?????????"),
                                fieldWithPath("appraisedUser.userId").description("???????????? ???????????? ????????? ?????? ?????????"),
                                fieldWithPath("appraisedUser.login").description("???????????? ???????????? ????????? ?????? ?????????"),
                                fieldWithPath("createdAt").description("????????? ??????")
                        )
                ))
        ;
    }
    @Test
    @DisplayName("BAD_REQUEST, ????????? ?????? ????????? ???????????? ")
    @WithMockUser(roles = {"STUDENT"})
    void findFeedbackWithSameUserId() throws Exception {
        User evalUser = saveUser("evalUser");
        User appraisedUser = saveUser("appraisedUser");
        Project savedProject = saveProject("testProject", "testProjectDescription");
        Feedback feedback  = saveFeedback(evalUser, appraisedUser, "testFeedback", 5, savedProject);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user",new SessionUser(evalUser));
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/project/{projectId}/feedback/user/{appraisedId}", 1L, 1L)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Transactional
    User saveUser(String name){
        User user = User.builder()
                .login(name)
                .role(Role.STUDENT)
                .build();
        return this.userRepository.save(user);
    }
    @Transactional
    Project saveProject(String name, String description){
        Project project = Project.builder()
                .description(description)
                .name(name)
                .build();
        return this.projectRepository.save(project);
    }
    @Transactional
    Feedback saveFeedback(User evalUser, User appraisedUser, String message, int star, Project project){
        return this.feedbackRepository.save(Feedback.createFeedback(evalUser, appraisedUser, message, star, project));
    }
}