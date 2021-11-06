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

    private void validateDuplicateUser(User user) {
        Optional<User> optionalUser = userRepository.findByLogin(user.getLogin());
        if (optionalUser.isPresent()) {
            throw new UserDuplicatedException(user.getLogin());
        }
    }

    public void save(Project project, List<UserCreateRequest> requestList) {
        for (UserCreateRequest request : requestList) {
            User user = User.builder()
                    .login(request.getLogin())
                    .build();
            //validateDuplicateUser(user);
            User savedUser = userRepository.save(user);
            Register register = Register.createRegister(
                    savedUser,
                    project);
            System.out.println(register);
            Register saved = registerRepository.save(register);
        }
    }
}
