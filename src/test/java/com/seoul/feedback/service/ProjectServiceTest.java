package com.seoul.feedback.service;

import com.seoul.feedback.dto.ProjectCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.repository.ProjectMemberRepository;
import com.seoul.feedback.repository.ProjectRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@SpringBootTest()
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // beforeall을 non-static으로 유지
class ProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;

    @BeforeAll
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    private Project project() {
        return Project.builder()
                .name("testname")
                .description("desctest")
                .build();
    }

    @Test
    @DisplayName("repository가 NUll이 아님")
    public void projectRepository_not_null() {
        assertNotNull(projectRepository);
    }

    @Test
    public void 프로젝트_등록() {


        // when 요청
        Project result = projectService.saveProject(new ProjectCreateRequest("testname", "desctest"));

        // then 예상되는 변화
        assertNotNull(result.getId());



    }
}