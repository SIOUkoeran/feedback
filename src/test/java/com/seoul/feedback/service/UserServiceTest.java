package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.exception.UserDuplicatedException;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    @DisplayName("login으로 유저조회")
    public void 유저_조회_with_login() {

        // given 테스트 하기 전 상태 or 조건 설명
        User user1 = User.builder()
                .login("eun-park")
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user1);

        //doReturn(user1).when(userRepository).findByLogin(any());

        // when
        userRepository.save(User.builder().login("eun-park").build());

        //then


    }
}