package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("repository가 NUll이 아님")
    public void userRepository_not_null() {
        assertNotNull(userRepository);
    }

    @Test
    @Commit // disable rollback
    @DisplayName("유저 첫 저장")
   void 유저_저장() {
        // given
        User user = User.builder()
                .login("eun-park")
                .build();

        // when
        User saved = userRepository.save(user);

        //then
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(user.getLogin(), saved.getLogin());
    }

    @Test
    @DisplayName("전체 유저 조회")
    void 유저_조회_all() {
        // given 테스트 하기 전 상태 or 조건 설명
        User user1 = User.builder()
                .login("eun-park")
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        this.userRepository.save(user1);
        // when
        List<User> findUserList = userRepository.findAll();

        //then
        Assertions.assertEquals(1, findUserList.size());
        Assertions.assertEquals(user1.getLogin(), findUserList.get(0).getLogin());
    }

    @Test
    @DisplayName("login으로 유저 조회")
    void 유저_조회_with_login() {
        // given 테스트 하기 전 상태 or 조건 설명
        User user1 = User.builder()
                .login("eun-park")
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user1);

        // when
        Optional<User> optionalUser = userRepository.findByLogin("eun-park");

        //then
//        Assertions.assertEquals(1, findUserList.size());
//        Assertions.assertEquals(user1.getLogin(), findUserList.get(0).getLogin());
    }

}