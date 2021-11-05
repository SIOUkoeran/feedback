package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.ProjectMember;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // beforeall을 non-static으로 유지
@ActiveProfiles("test")
class ProjectRepositoryTest {

    /*
    @ActiveProfiles("test") : application-test.yml에 등록된 DB를 사용한다. local DB와 분리된 테스트가 가능하다.
    */

    @Autowired
    ProjectRepository projectRepository;

    static List<ProjectMember> projectMemberList;

    @BeforeAll
    public static void setup() {
        projectMemberList = new ArrayList<>();
        projectMemberList.add(ProjectMember.builder()
                .login("eun-park")
                .build());

        projectMemberList.add(ProjectMember.builder()
                .login("seokim")
                .build());
    }

    @Test
    @DisplayName("repository가 NUll이 아님")
    public void projectRepository_not_null() {
        assertNotNull(projectRepository);
    }

    @Test
    @DisplayName("project save w/o member")
    public void 프로젝트_저장_wo_멤버() {

        //given
        Project project = new Project("project2", "desc313");

        //when
        Project saved = projectRepository.save(project);

        //then
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(project.getName(), saved.getName());

    }

    @Test
    @DisplayName("project save with member")
    public void 프로젝트_저장_with_멤버() {

        //given
        Project project = Project.builder()
                .name("wowow3322")
                .description("wowdesc22")
                .projectMemberList(projectMemberList)
                .build();

        //when
        Project saved = projectRepository.save(project);

        //then
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(project.getName(), saved.getName());


    }
}