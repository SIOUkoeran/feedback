package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Project;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // beforeall을 non-static으로 유지
@ActiveProfiles("test")
class ProjectRepositoryTest {

    /*
    @ActiveProfiles("test") : application-test.yml에 등록된 DB를 사용한다. local DB와 분리된 테스트가 가능하다.
    */

    @Autowired
    ProjectRepository projectRepository;

    //List<ProjectMember> projectMemberList;

    @BeforeAll
    public void setup() {
//        projectMemberList = new ArrayList<>();
//        projectMemberList.add(ProjectMember.builder()
//                .login("eun-park")
//                .build());
//
//        projectMemberList.add(ProjectMember.builder()
//                .login("seokim")
//                .build());
    }

    @Test
    @DisplayName("repository가 NUll이 아님")
    public void projectRepository_not_null() {
        assertNotNull(projectRepository);
    }

    @Test
    @Commit
    @DisplayName("프로젝트 첫 저장")
    public void 프로젝트_저장() {

        //given
        Project project = Project.builder()
                .name("algo")
                .description("algo desc")
                .build();

        //when
        Project saved = projectRepository.save(project);

        //then
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(project.getName(), saved.getName());

    }

    @Test
    void 프로젝트_조회_사이즈가_1() {
        // given

        // when
        List<Project> result = projectRepository.findAll();

        // then
        assertEquals(result.size(), 1);
    }

}