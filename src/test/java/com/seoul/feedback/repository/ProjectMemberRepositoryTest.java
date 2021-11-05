package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.ProjectMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectMemberRepositoryTest {

    @Autowired
    ProjectMemberRepository projectMemberRepository;


    @Test
    @DisplayName("repository가 NUll이 아님")
    public void projectMemberRepository_not_null() {
        assertNotNull(projectMemberRepository);
    }

    @Test
    @DisplayName("projectMember가 잘 등록되는지")
    public void 프로젝트_멤버_등록() {
        System.out.println(projectMemberRepository);

        // given
        ProjectMember projectMember = ProjectMember
                .builder()
                .login("eun-park")
                .build();

        // when
        ProjectMember saved = projectMemberRepository.save(projectMember);

        // then
        assertNotNull(saved.getId());
        assertEquals("eun-park", saved.getLogin());
    }


}