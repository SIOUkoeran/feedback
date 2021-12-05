package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.UserCreateRequest;
import com.seoul.feedback.dto.response.RegisterResponse;
import com.seoul.feedback.dto.response.UserResponse;
import com.seoul.feedback.entity.Feedback;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.entity.enums.RegisterStatus;
import com.seoul.feedback.exception.EntityNotFoundException;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.UserRepository;
import com.seoul.feedback.security.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final HttpSession httpSession;

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

    @Transactional(readOnly = true)
    public List<RegisterResponse> findByUserId(Long userId){
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.get().getRegisterList().size() == 0){
            throw new EntityNotFoundException("Registered Project not found");
        }
        return user.get().getRegisterList().stream()
                .filter(register -> register.getStatus() == RegisterStatus.REGISTER)
                .map(register -> RegisterResponse.builder()
                        .register(register)
                        .build())
                .collect(Collectors.toList())
                ;

    }


    /**
     *
     * 리팩토링 필요
     * 페치 조인으로 SQL 성능 개선 필요
     * ... 테이블 비졍규화 필요 + 1
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserResponse.Project> getUserListByProjectId(Long projectId, Long userId){
        Optional<Project> project = this.projectRepository.findById(projectId);
        Optional<User> user = this.userRepository.findById(userId);

        if (project.isEmpty())
        {
            throw new EntityNotFoundException("there is no registered project");
        }
        if (user.isEmpty())
        {
            throw new EntityNotFoundException("");
        }
        return project.get().getRegisterList()
                .stream()
                .filter(register -> register.getStatus() == RegisterStatus.REGISTER)
                .map(register -> register.getUser())
                .filter(user1 -> user1.getId() != userId)
                .map(user1 -> {
                    Optional<User> user2 = user1.getReceivedFeedback().stream()
                            .filter(feedback -> feedback.getEvalUser().getId() == userId &&
                                    feedback.getProject().getId() == projectId)
                            .map(Feedback::getAppraisedUser)
                            .findFirst();
                    if (user2.isEmpty()){
                        return new UserResponse.Project(user1.getId(), user1.getLogin(),false);
                    }
                    else{
                        return new UserResponse.Project(user1.getId(), user1.getLogin(),true);
                    }
                }
        ).collect(Collectors.toList());
    }

    public User findBySessionUser(HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        Optional<User> findUser = this.userRepository.findByLogin(sessionUser.getLogin());
        if (findUser.isEmpty()){
            throw new EntityNotFoundException("user not Found! login is something wrong!");
        }
        return findUser.get();
    }
}
