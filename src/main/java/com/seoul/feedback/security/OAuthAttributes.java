package com.seoul.feedback.security;



import com.seoul.feedback.entity.User;
import com.seoul.feedback.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String login;

    @Builder
    public  OAuthAttributes(Map<String, Object> attributes,
                            String nameAttributeKey,
                            String login){
        this.login = login;
        this.nameAttributeKey = nameAttributeKey;
        this.attributes = attributes;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .login((String) attributes.get("login"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    public User toEntity() {
        return new User(login ,Role.STUDENT);
    }
}
