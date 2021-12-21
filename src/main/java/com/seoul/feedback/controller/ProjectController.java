package com.seoul.feedback.controller;

import com.seoul.feedback.dto.request.ProjectCreateRequest;
import com.seoul.feedback.dto.request.ProjectUpdateRequest;
import com.seoul.feedback.dto.response.CommonResponse;
import com.seoul.feedback.dto.response.project.ProjectResponse;
import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.service.ProjectService;
import com.seoul.feedback.service.RegisterService;
import com.seoul.feedback.service.UserService;
import com.seoul.feedback.service.session.SessionUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json; charset=UTF-8")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final RegisterService registerService;
    private final HttpSession httpSession;
    private final SessionUserService sessionUserService;

    @PostMapping(value = "/project")
    public ProjectResponse create(@RequestBody ProjectCreateRequest request) {
        Project project = projectService.save(request);
        userService.saveAll(request.getUserList());
        registerService.saveAll(project.getId(), request.getUserList());
        return new ProjectResponse(project);
    }

    @GetMapping(value = "/project")
    public ResponseEntity getProjectsByUser(){
        User user = sessionUserService.findBySessionUser(httpSession);
        return ResponseEntity.ok().body(this.userService.findByUserId(user.getId()).stream()
                .map(registerResponse -> this.projectService
                        .findRegisteredProjectById(registerResponse.getProjectId())
                ).collect(Collectors.toList())) ;

    }

//    @GetMapping("/project/user/{projectId}/{userId}")
//    public ProjectResponse.Users getProjectByUser(@PathVariable Long projectId,
//                                            @PathVariable Long userId)
//    {
//
//
//    }


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
        userService.saveAll(request.getUserList());
        registerService.update(project.getId(), request.getUserList());
        return new ProjectResponse(project);
    }

    @DeleteMapping(value = "/project/{projectId}")
    public CommonResponse deleteProject(@PathVariable Long projectId) {
        projectService.delete(projectId);
        registerService.delete(projectId);
        return CommonResponse.builder()
                .message("deleted")
                .statusCode(200)
                .build();
    }
}

