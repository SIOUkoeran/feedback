package com.seoul.feedback.dto.response;

import com.seoul.feedback.entity.Project;
import com.seoul.feedback.entity.RegisterStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProjectResponse {
    private Long projectId;
    private String name;
    private String description;
    private List<RegisterResponse> userList;

    @Builder
    public ProjectResponse(Project project) {
        this.projectId = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.userList = project.getRegisterList()
                .stream()
                .filter(register -> register.getStatus() == RegisterStatus.REGISTER)
                .map(register -> RegisterResponse.builder()
                        .register(register)
                        .build())
                .collect(Collectors.toList());

    }

}



