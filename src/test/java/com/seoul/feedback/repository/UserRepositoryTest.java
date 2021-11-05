package com.seoul.feedback.repository;

import com.seoul.feedback.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
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

}