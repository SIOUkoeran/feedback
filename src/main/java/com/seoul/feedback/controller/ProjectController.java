package com.seoul.feedback.controller;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.response.ProjectResponse;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.service.ProjectService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(value = "/project")
    public ProjectResponse create(@RequestBody ProjectCreateRequest request) {
        System.out.println(request);
        return new ProjectResponse(projectService.save(request));
    }

    @GetMapping(value = "/projects")
    public List<ProjectResponse> list() {
        return projectService.list();
    }

    @GetMapping(value = "/project/{projectId}")
    public ProjectResponse getProject(@PathVariable Long projectId) {
        return projectService.findById(projectId);
    }

}

