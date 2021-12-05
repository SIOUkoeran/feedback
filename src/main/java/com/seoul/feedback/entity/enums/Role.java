package com.seoul.feedback.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    STUDENT("ROLE_GUEST", "학생"),
    ROLE_ADMIN("ROLE_ADMIN", "멘토");
    private final String key;
    private final String title;
}
