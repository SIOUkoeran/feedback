package com.seoul.feedback.dto.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectCreateRequest {
    private String name;
    private String description;
    private List<UserCreateRequest> userList;

    public ProjectCreateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProjectCreateRequest(String name, String description, List<UserCreateRequest> userList) {
        this.name = name;
        this.description = description;
        this.userList = userList;
    }
}
