package com.seoul.feedback.dto.response;

import com.seoul.feedback.entity.Project;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private List<RegisterResponse> registerResponseList;

    @Builder
    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.registerResponseList = project.getRegisterList()
                        .stream()
                        .map(register -> RegisterResponse.builder()
                            .id(register.getId())
                            .login(register.getUser().getLogin())
                            .build())
                            .collect(Collectors.toList());

    }

}
