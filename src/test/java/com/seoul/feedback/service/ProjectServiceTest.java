package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    /*
    실제 데이터베이스가 아닌 mock repository를 사용한다
     */

    @InjectMocks // 테스트 대상
    ProjectService projectService;

    @Mock // 테스트 도와주는 의존성
    ProjectRepository projectRepository;

    private Project project() {
        return Project.builder()
                .name("testname")
                .description("desctest")
                .build();
    }

    @Test
    @DisplayName("repository가 null이 아님")
    public void projectRepository_not_null() {
        assertNotNull(projectRepository);
    }

    @Test
    @DisplayName("쌩 프로젝트만 등록")
    public void 프로젝트_등록() {

        // given 테스트 하기 전 상태 or 조건 설명
        doReturn(project()).when(projectRepository).save(any(Project.class));

        // when 요청
        Project result = projectService.save(new ProjectCreateRequest("eun-park", "what"));

        // then
        assertEquals(result.getName(), "testname");
        assertEquals(result.getDescription(), "desctest");

        // verify 생성된 mock은 자신의 모든 행동을 기억하는데, verify()를 이용해서 원하는 메소드가 특정 조건으로 실행되었는지를 검증할 수 있다.
        verify(projectRepository, times(1)).save(any(Project.class));

    }

    @Test
    @DisplayName("프로젝트 등록 시 팀 1개를 만든다")
    public void 프로젝트_등록_with_팀() {

        // given 테스트 하기 전 상태 or 조건 설명
        doReturn(project()).when(projectRepository).save(any(Project.class));

        // when 요청
        Project result = projectService.save(new ProjectCreateRequest("eun-park", "what"));

        // then
        assertEquals(result.getName(), "testname");
        assertEquals(result.getDescription(), "desctest");

        // verify 생성된 mock은 자신의 모든 행동을 기억하는데, verify()를 이용해서 원하는 메소드가 특정 조건으로 실행되었는지를 검증할 수 있다.
        verify(projectRepository, times(1)).save(any(Project.class));

    }

    @Test
    @DisplayName("findAll project")
    void 프로젝트_전체조회() {

        //given
        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project("eun-park", "desc"));
        projectList.add(new Project("eun-park2", "desc2"));
        doReturn(projectList).when(projectRepository).findAll();

        //when
        List<Project> foundList = projectRepository.findAll();

        //then
        assertEquals(foundList.size(), 2);
        assertEquals(foundList.get(0).getName(), "eun-park");
        assertEquals(foundList.get(1).getName(), "eun-park2");

    }
}