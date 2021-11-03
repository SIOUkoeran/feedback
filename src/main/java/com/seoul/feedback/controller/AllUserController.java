package com.seoul.feedback.controller;

import com.seoul.feedback.dto.Token;
import com.seoul.feedback.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AllUserController {

    private final RestTemplate restTemplate;

    @Value("${42.user-code}")
    private String userCode;

    @Value("${42.client-id}")
    private String clientId;

    @Value("${42.client-secret}")
    private String clientSecret;

    @Value("${42.redirect-uri}")
    private String redirectUri;

    @Value("${42.access-token}")
    private String accessToken;


    private String requestQuery =
            "grant_type=" + "authorization_code" +
            "&" + "code=" + userCode +
            "&" + "client_id=" + clientId +
            "&" + "client_secret=" + clientSecret +
            "&" + "redirect_uri=" + redirectUri +
            "&" + "scope=" + "public";

    private String getAccessToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<Token> tokenResponseEntity = restTemplate.exchange(
                "https://api.intra.42.fr/oauth/token?" + requestQuery,
                HttpMethod.POST,
                new HttpEntity(httpHeaders),
                Token.class);

        return tokenResponseEntity.getBody().getAccess_token();
    }

    private List<User> sendApiRequest(String accessToken) {
        // 헤더
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", accessToken);

        // api 요청
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "https://api.intra.42.fr/v2/campus/29/users",
                HttpMethod.GET,
                new HttpEntity(httpHeaders),
                new ParameterizedTypeReference<List<User>>() {}
        );
        List<User> userList = response.getBody();
        return userList;
    }


    @RequestMapping("/user")
    public List<User> getAllUsers() {
        //String accessToken = getAccessToken();
        List<User> userList = sendApiRequest(accessToken);
        return userList;
    }
}
