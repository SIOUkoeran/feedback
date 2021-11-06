package com.seoul.feedback.controller;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
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
    public Project create(@RequestBody ProjectCreateRequest request) {
        return projectService.save(request);
    }

    @GetMapping(value = "/project")
    public List<Project> list() {
        return projectService.list();
    }

}

