package com.seoul.feedback.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectUpdateRequest {
    private String name;
    private String description;
    private List<UserCreateRequest> userCreateRequestList;

    public ProjectUpdateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProjectUpdateRequest(String name, String description, List<UserCreateRequest> userCreateRequestList) {
        this.name = name;
        this.description = description;
        this.userCreateRequestList = userCreateRequestList;
    }
}
