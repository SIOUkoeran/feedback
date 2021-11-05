package com.seoul.feedback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.service.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // beforeall을 non-static으로 유지
class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    ProjectService projectService;

    @InjectMocks
    ProjectController projectController;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    @DisplayName("프로젝트 post 요청")
    public void 프로젝트_post() throws Exception {

        mockMvc.perform(post("/api/v1/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectCreateRequest("project100", "desc100"))))
                .andExpect(MockMvcResultMatchers.content().string("project100, desc100"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}