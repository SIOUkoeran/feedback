package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.response.ProjectResponse;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.exception.EntityNotFoundException;
import com.seoul.feedback.repository.ProjectRepository;
import com.seoul.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final RegisterService registerService;

    public List<ProjectResponse> list() {
        List<Project> projectList = projectRepository.findAll();
        return projectList.stream().map(project -> ProjectResponse.builder()
                .project(project)
                .build())
                .collect(Collectors.toList());
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

    public ProjectResponse findById(Long projectId) {
        return new ProjectResponse(projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found")));
    }

}
