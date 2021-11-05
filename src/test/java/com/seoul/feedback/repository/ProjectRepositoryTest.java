package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.ProjectMember;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith(SpringExtension.class)
@SpringBootTest
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional // rolled back transaction for test
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // beforeall을 non-static으로 유지
@ActiveProfiles("test")
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    List<ProjectMember> projectMemberList;

    @BeforeAll
    public void setup() {
        projectMemberList = new ArrayList<>();
        projectMemberList.add(ProjectMember.builder()
                .login("eun-park")
                .build());

        projectMemberList.add(ProjectMember.builder()
                .login("login")
                .build());
    }

    @Test
    @DisplayName("project가 잘 저장 되는지")
    public void 프로젝트_저장_with_멤버() {

        //given
        Project project = Project.builder()
                .name("project1")
                .description("desc1")
                .projectMemberList(projectMemberList)
                .build();

        //when
        Project saved = projectRepository.save(project);

        //then
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(project.getName(), saved.getName());

    }
}