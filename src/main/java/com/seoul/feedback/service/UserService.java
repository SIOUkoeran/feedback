package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.dto.response.ProjectResponse;
import com.seoul.feedback.dto.response.RegisterResponse;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.Register;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.exception.EntityNotFoundException;
import com.seoul.feedback.exception.UserDuplicatedException;
import com.seoul.feedback.repository.RegisterRepository;
import com.seoul.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<RegisterResponse> findByUserId(Long userId){
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new EntityNotFoundException("User not found"));

        return user.get().getRegisterList().stream().map(
                register -> RegisterResponse.builder()
                        .id(register.getId())
                        .login(user.get().getLogin())
                        .projectId(register.getProject().getId())
                        .build())
                .collect(Collectors.toList());

    }
}
