package com.seoul.feedback.controller;

import com.seoul.feedback.dto.ProjectCreateRequest;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.service.ProjectService;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(value = "/project")
    public Project create(@RequestBody ProjectCreateRequest request) {
        return projectService.saveProject(request);
    }

}

