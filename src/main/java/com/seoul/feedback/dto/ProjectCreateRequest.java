package com.seoul.feedback.dto;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateRequest {
    private String name;
    private String description;
}
