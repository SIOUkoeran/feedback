package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> list() {
        return projectRepository.findAll();
    }

    @Transactional
    public Project save(ProjectCreateRequest request) {
        return projectRepository.save(Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build());
    }

}
