package com.seoul.feedback.dto.response;

import com.seoul.feedback.entity.Project;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectSimpleResponse {
    private Long projectId;
    private String name;
    private String description;

    @Builder
    public ProjectSimpleResponse(Project project){
        this.projectId = project.getId();
        this.name= project.getName();
        this.description = project.getDescription();
    }
}
