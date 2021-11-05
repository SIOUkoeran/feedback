package com.seoul.feedback.service;

import com.seoul.feedback.dto.ProjectCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.repository.ProjectMemberRepository;
import com.seoul.feedback.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project saveProject(ProjectCreateRequest request) {
        Project project = Project.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .build();
        return projectRepository.save(project);
    }

}
