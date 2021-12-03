package com.seoul.feedback.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails.UserInfoEndpoint;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        UserInfoEndpoint userInfoEndpoint =
                userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint();

        String userInfoUri = userInfoEndpoint.getUri();

        String nameAttributeKey = userInfoEndpoint.getUserNameAttributeName();

        Map<String, Object> attributes = getAttributes(userRequest);
        Set<GrantedAuthority> authorities = getAuthorities(userRequest, attributes);

        return Oauth2User42.builder()
                .authorities(authorities)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }


}
