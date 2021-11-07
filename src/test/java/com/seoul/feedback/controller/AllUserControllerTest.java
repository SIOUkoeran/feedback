package com.seoul.feedback.controller;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AllUserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // init 메소드를 non-static으로 유지하기 위한 어노테이션
class AllUserControllerTest {

    @Autowired
    MockMvc mockMvc;

//    @Mock
//    private RestTemplate restTemplate;
//
//    private MockRestServiceServer mockServer;
//
//    @BeforeAll
//    public void init() {
//        mockServer = MockRestServiceServer.createServer(restTemplate);
//    }

    @Test
    @DisplayName("42OAuth getToken 테스트")
    void getAccessToken() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();
    }
//        mvc.per
//        mvc.perform(get("/user")) .andExpect(status().isOk()) .andExpect(content().string(containsString("John"))); }

}