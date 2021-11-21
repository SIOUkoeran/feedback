package com.seoul.feedback.common;


import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(com.seoul.feedback.common.RestDocsConfiguration.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class BaseControllerTest {
}
