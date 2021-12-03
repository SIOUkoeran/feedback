package com.seoul.feedback.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class Oauth2User42 implements CustomOAuth2User{

    @Override
    public String getOAuth2Id() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getNickName() {
        return null;
    }

    @Override
    public String getNameAttributeKey() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
