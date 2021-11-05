package com.seoul.feedback.repository;

import com.seoul.feedback.entity.ProjectMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectUserRepositoryTest {

    /*
    @DataJpaTest: 인메모리 데이터베이스를 사용하여 실제 데이터베이스를 사용하지 않음
    만약에 실제 데이터베이스에 테스트하고 싶으면: replace = AutoConfigureTestDatabase.Replace.NONE
    테스트 후 자동 롤백되지만, 증가한 id 값은 남아있다.
    */

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