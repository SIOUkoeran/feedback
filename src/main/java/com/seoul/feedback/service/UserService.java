package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.Register;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.exception.UserDuplicatedException;
import com.seoul.feedback.repository.RegisterRepository;
import com.seoul.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RegisterRepository registerRepository;

    private boolean validateDuplicateUser(User user) {
        Optional<User> optionalUser = userRepository.findByLogin(user.getLogin());
        if (optionalUser.isPresent()) {
            return true;
            //throw new UserDuplicatedException(user.getLogin());
        }
        return false;
    }

    public void save(UserCreateRequest request) {
        User user = User.builder()
                .login(request.getLogin())
                .build();
        if (!validateDuplicateUser(user)) {
            userRepository.save(user);
        }
    }

    public void saveAll(List<UserCreateRequest> requestList) {
        for (UserCreateRequest request : requestList) {
            save(request);
        }
    }
}
