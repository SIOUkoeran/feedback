package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    /*
    실제 데이터베이스가 아닌 mock repository를 사용한다
     */

    @InjectMocks // 테스트 대상
    UserService userService;

    @Mock // 테스트 도와주는 의존성
    UserRepository userRepository;

    private User user() {
        return User.builder()
                .login("seokim")
                .build();
    }

    @Test
    @DisplayName("repository가 null이 아님")
    public void projectRepository_not_null() {
        assertNotNull(userRepository);
    }

    @Test
    @DisplayName("유저 첫 저장")
    public void 유저_저장() {

        // given 테스트 하기 전 상태 or 조건 설명
        doReturn(user()).when(userRepository).save(any(User.class));

        // when 요청
        User result = userService.save(new UserCreateRequest("bear"));

        // then
        assertEquals(result.getLogin(), "seokim");

        // verify 생성된 mock은 자신의 모든 행동을 기억하는데, verify()를 이용해서 원하는 메소드가 특정 조건으로 실행되었는지를 검증할 수 있다.
        verify(userRepository, times(1)).save(any(User.class));

    }
}