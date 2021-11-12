package com.seoul.feedback.controller;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.ProjectUpdateRequest;
import com.seoul.feedback.dto.response.ProjectResponse;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.service.ProjectService;
import com.seoul.feedback.service.RegisterService;
import com.seoul.feedback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final RegisterService registerService;

    @PostMapping(value = "/project")
    public ProjectResponse create(@RequestBody ProjectCreateRequest request) {
        Project project = projectService.save(request);
        userService.saveAll(request.getUserCreateRequestList());
        registerService.saveAll(project.getId(), request.getUserCreateRequestList());
        return new ProjectResponse(project);
    }

    @GetMapping(value = "/projects")
    public List<ProjectResponse> list() {
        return projectService.list();
    }

    @GetMapping(value = "/project/{projectId}")
    public ProjectResponse getProject(@PathVariable Long projectId) {
        return projectService.findById(projectId);
    }

    @PutMapping(value = "/project/{projectId}")
    public ProjectResponse updateProject(@PathVariable Long projectId,
                                         @RequestBody ProjectUpdateRequest request) {
        Project project = projectService.update(projectId, request);
        userService.saveAll(request.getUserCreateRequestList());
        registerService.update(project.getId(), request.getUserCreateRequestList());
        return new ProjectResponse(project);
    }
}

