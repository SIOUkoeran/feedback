package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final RegisterService registerService;

    public List<Project> list() {
        return projectRepository.findAll();
    }

    @Transactional
    public Project save(ProjectCreateRequest request) {
        Project savedProject = projectRepository.save(Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build());
        userService.save(savedProject, request.getUserCreateRequestList());
        return savedProject;
    }

}
