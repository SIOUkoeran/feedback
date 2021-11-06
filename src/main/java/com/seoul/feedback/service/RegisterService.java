package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.Register;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.RegisterRepository;
import com.seoul.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final RegisterRepository registerRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;


    /* 등록 */
    @Transactional
    public void save(Long projectId, String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalUser.isPresent() && optionalProject.isPresent()) {
            Register register = Register.createRegister(
                    optionalUser.get(),
                    optionalProject.get());
            Register saved = registerRepository.save(register);
        }
    }

    public void saveAll(Long projectId, List<UserCreateRequest> requestList) {
        for (UserCreateRequest request : requestList) {
            save(projectId, request.getLogin());
        }
    }

}
