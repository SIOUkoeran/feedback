package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.Register;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.exception.EntityNotFoundException;
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

    public void save(Long projectId, String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalUser.isPresent() && optionalProject.isPresent()) {
            Register register = Register.createRegister(
                    optionalUser.get(),
                    optionalProject.get());
            registerRepository.save(register);
        }
    }

    @Transactional
    public void saveAll(Long projectId, List<UserCreateRequest> requestList) {
        for (UserCreateRequest request : requestList) {
            save(projectId, request.getLogin());
        }
    }


    public void update(Long projectId, List<UserCreateRequest> requestList) {
        List<Register> registerList = registerRepository.findByProjectId(projectId);
        for (Register register : registerList) {
            register.cancel();
        }
        saveAll(projectId, requestList);
    }


    @Transactional
    public void delete(Long projectId) {
        List<Register> registerList = registerRepository.findByProjectId(projectId);
        for (Register register : registerList) {
            register.cancel();
        }
    }
}
