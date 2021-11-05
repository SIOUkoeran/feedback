package com.seoul.feedback.service;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.ProjectMemberCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.ProjectMember;
import com.seoul.feedback.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;


    private ProjectMember projectMemberToEntity(ProjectMemberCreateRequest request) {
        return ProjectMember.builder()
                .login(request.getLogin())
                .build();
    }

    private List<ProjectMember> projectMemberListToEntity(List<ProjectMemberCreateRequest> request) {
        List<ProjectMember> projectMemberList = new ArrayList<>();
        for (ProjectMemberCreateRequest projectMember : request) {
            projectMemberList.add(projectMemberToEntity(projectMember));
        }
        return projectMemberList;
    }

    private Project projectToEntity(String name, String description, List<ProjectMember> projectMemberList) {
        return Project.builder()
                .name(name)
                .description(description)
                .build();
    }

    @Transactional
    public Project save(ProjectCreateRequest request) {
        List<ProjectMember> projectMemberList = projectMemberListToEntity(request.getProjectMemberCreateList());
        return projectRepository.save(projectToEntity(request.getName(), request.getDescription(), projectMemberList));
    }

}
