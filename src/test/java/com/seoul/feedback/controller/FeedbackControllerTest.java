package com.seoul.feedback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoul.feedback.common.BaseControllerTest;
import com.seoul.feedback.dto.request.FeedbackCreateRequest;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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




    @Test
    public void getFeedbackList() throws Exception{
        User evalUser = User.builder()
                .login("evalTest")
                .build();
        this.userRepository.save(evalUser);
        User appraisedUser = User.builder()
                .login("appraiseTest")
                .build();
        this.userRepository.save(appraisedUser);
        Project project = Project.builder()
                .description("testProject")
                .name("project")
                .build();
        this.projectRepository.save(project);
        Feedback.createFeedback(evalUser, appraisedUser, "좋아요", 5, project);
        Feedback feedback = this.feedbackRepository.save(Feedback.createFeedback(evalUser, appraisedUser, "좋아요", 5, project));
        mockMvc.perform(get("/v1/api/project/{projectId}/feedbacks", 1L)
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
                                fieldWithPath("projectId").description("프로젝트 아이디"),
                                fieldWithPath("feedbackResponses[0].feedbackId").description("피드백 아이디"),
                                fieldWithPath("feedbackResponses[0].evalUser.userId").description("평가 유저 아이디"),
                                fieldWithPath("feedbackResponses[0].evalUser.login").description("평가 유저 로그인"),
                                fieldWithPath("feedbackResponses[0].appraisedUser.userId").description("피평가 유저 아이디"),
                                fieldWithPath("feedbackResponses[0].appraisedUser.login").description("피평가 유저 로그인"),
                                fieldWithPath("feedbackResponses[0].message").description("평가 메세지"),
                                fieldWithPath("feedbackResponses[0].star").description("평가 점수")
                        )
                ))

        ;

    }
    @Test
    void 평가유저피드백() throws Exception{
        User evalUser = User.builder()
                .login("evalTest")
                .build();
        this.userRepository.save(evalUser);
        System.out.println("evalUser.getId() = " + evalUser.getId());
        User appraisedUser = User.builder()
                .login("appraiseTest")
                .build();
        this.userRepository.save(appraisedUser);
        Project project = Project.builder()
                .description("testProject")
                .name("project")
                .build();
        this.projectRepository.save(project);

        this.feedbackRepository.save(Feedback.createFeedback(evalUser, appraisedUser, "좋아요", 5, project));
        mockMvc.perform(get("/v1/api/user/{userId}/evalFeedbacks", 1L)
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
                                fieldWithPath("evalFeedbackList[0].projectId").description("프로젝트 아이디"),
                                fieldWithPath("evalFeedbackList[0].feedbackId").description("프로젝트 아이디"),
                                fieldWithPath("evalUserId").description("평가자 아이디"),
                                fieldWithPath("evalUserLogin").description(" 평가자 로그인"),
                                fieldWithPath("evalFeedbackList[0].appraisedUser.userId").description("피평가 아이디"),
                                fieldWithPath("evalFeedbackList[0].appraisedUser.login").description("피평가 로그인"),
                                fieldWithPath("evalFeedbackList[0].message").description("프로젝트 메세지"),
                                fieldWithPath("evalFeedbackList[0].star").description("프로젝트 평가 점수")
                        )
                ))
        ;

    }

    @Test
    void 피평가유저피드백() throws Exception{
        User evalUser = User.builder()
                .login("evalTest")
                .build();
        this.userRepository.save(evalUser);
        System.out.println("evalUser.getId() = " + evalUser.getId());
        User appraisedUser = User.builder()
                .login("appraiseTest")
                .build();
        this.userRepository.save(appraisedUser);
        Project project = Project.builder()
                .description("testProject")
                .name("project")
                .build();
        this.projectRepository.save(project);

        this.feedbackRepository.save(Feedback.createFeedback(evalUser, appraisedUser, "좋아요", 5, project));
        mockMvc.perform(get("/v1/api/user/{userId}/appraisedFeedbacks", 2L)
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
                                fieldWithPath("appraisedUserId").description("평가 받은 유저 아이디"),
                                fieldWithPath("appraisedUserLogin").description("평가 받은 유저 로그인"),
                                fieldWithPath("appraiseFeedbackList[0].projectId").description("프로젝트 아이디"),
                                fieldWithPath("appraiseFeedbackList[0].feedbackId").description("프로젝트 아이디"),
                                fieldWithPath("appraiseFeedbackList[0].evalUser.userId").description("평가한 유저 아이디"),
                                fieldWithPath("appraiseFeedbackList[0].evalUser.login").description(" 평가한 유저 로그인"),
                                fieldWithPath("appraiseFeedbackList[0].message").description("프로젝트 메세지"),
                                fieldWithPath("appraiseFeedbackList[0].star").description("프로젝트 평가 점수")
                        )
                ))
        ;

    }

    @Test
    void 피드백생성() throws Exception {
        User evalUser = User.builder()
                .login("evalTest")
                .build();
        this.userRepository.save(evalUser);

        User appraisedUser = User.builder()
                .login("appraiseTest")
                .build();
        this.userRepository.save(appraisedUser);
        Project project = Project.builder()
                .description("testProject")
                .name("project")
                .build();
        Project p = this.projectRepository.save(project);
        FeedbackCreateRequest feedbackCreateRequest = new
                FeedbackCreateRequest(evalUser.getId(), appraisedUser.getId(), "무야호", 3);
        mockMvc.perform(post("/v1/api/project/{projectId}/feedback", p.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(feedbackCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void 피드백리스트확인() throws Exception {
        User evalUser = User.builder()
                .login("evalTest")
                .build();
        this.userRepository.save(evalUser);
        System.out.println("evalUser.getId() = " + evalUser.getId());
        User appraisedUser = User.builder()
                .login("appraiseTest")
                .build();
        this.userRepository.save(appraisedUser);
        Project project = Project.builder()
                .description("testProject")
                .name("project")
                .build();
        this.projectRepository.save(project);
        this.feedbackRepository.save(Feedback.createFeedback(evalUser, appraisedUser, "좋아요", 5, project));
        this.feedbackRepository.save(Feedback.createFeedback(evalUser, appraisedUser, "아주아요좋아요", 5, project));

        mockMvc.perform(get("/v1/api/project/{projectId}/feedbacks", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }
}