package com.seoul.feedback.service.session;

import com.seoul.feedback.entity.User;
import com.seoul.feedback.exception.EntityNotFoundException;
import com.seoul.feedback.repository.UserRepository;
import com.seoul.feedback.security.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionUserService implements OAuth2UserService{

    private final UserRepository userRepository;

    @Override
    public User findBySessionUser(HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        Optional<User> findUser = this.userRepository.findByLogin(sessionUser.getLogin());
        if (findUser.isEmpty()){
            throw new EntityNotFoundException("user not Found! login is something wrong!");
        }
        return findUser.get();
    }
}
