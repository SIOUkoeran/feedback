package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.ProjectUpdateRequest;
import com.seoul.feedback.dto.response.project.ProjectResponse;
import com.seoul.feedback.dto.response.project.ProjectSimpleResponse;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.enums.ProjectStatus;
import com.seoul.feedback.exception.EntityNotFoundException;
import com.seoul.feedback.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponse> list() {
        List<Project> findProjectList = projectRepository.findAll();
        return findProjectList.stream()
                .filter(project -> project.getStatus() == ProjectStatus.CREATE)
                .map(project -> ProjectResponse.builder()
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
        return savedProject;
    }

    public ProjectResponse findById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return new ProjectResponse(project.orElseThrow(
                () -> new EntityNotFoundException("Project not found")));
    }

    public ProjectSimpleResponse findRegisteredProjectById(Long projectId){

        return ProjectSimpleResponse.builder().project(this.projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found")
        )).build();
    }

    @Transactional
    public Project update(Long projectId, ProjectUpdateRequest request) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        project.update(request.getName(), request.getDescription());
        return projectRepository.save(project);
    }


    @Transactional
    public void delete(Long projectId) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        project.cancel();
    }
}
