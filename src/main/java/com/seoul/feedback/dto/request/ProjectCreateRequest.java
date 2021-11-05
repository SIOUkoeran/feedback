package com.seoul.feedback.dto.request;

import com.seoul.feedback.entity.ProjectMember;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
public class ProjectCreateRequest {
    private String name;
    private String description;
    private List<ProjectMemberCreateRequest> projectMemberCreateList;

    public ProjectCreateRequest() {
    }

    public ProjectCreateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProjectCreateRequest(String name, String description, List<ProjectMemberCreateRequest> projectMemberCreateList) {
        this.name = name;
        this.description = description;
        this.projectMemberCreateList = projectMemberCreateList;
    }
}
