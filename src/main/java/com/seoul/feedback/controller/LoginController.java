package com.seoul.feedback.controller;

import com.seoul.feedback.dto.Token;
import com.seoul.feedback.dto.response.UserResponse;
import com.seoul.feedback.service.session.OAuth2SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final OAuth2SessionService oAuth2SessionService;
    private final HttpSession httpSession;

    @Autowired
    public LoginController(OAuth2SessionService oAuth2SessionService, HttpSession httpSession) {
        this.oAuth2SessionService =  oAuth2SessionService;
        this.httpSession = httpSession;
    }
    @Value("${42.client-id}")
    private String clientId;

    @Value("${42.client-secret}")
    private String clientSecret;

    @Value("${42.redirect-uri}")
    private String redirectUri;

    @Value("${42.access-token}")
    private String accessToken;

    @GetMapping("/login")
    public ResponseEntity cadetLogin() throws IOException {
        return ResponseEntity.ok(UserResponse.builder()
                .user(oAuth2SessionService.findBySessionUser(httpSession))
                .build());
    }

    @GetMapping("/user/login")
    public Token getLoginAccess(@RequestParam(name = "code") String code){

        System.out.println("code = " + code);
        String requestQuery =
                "grant_type=" + "authorization_code" +
                        "&" + "client_id=" + clientId +
                        "&" + "client_secret=" + clientSecret +
                        "&" + "code=" + code +
                        "&" + "redirect_uri=" + redirectUri;

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<Token> responseEntity = restTemplate.exchange("https://api.intra.42.fr/oauth/token?" + requestQuery, HttpMethod.POST,
                new HttpEntity(httpHeaders), Token.class);

        return responseEntity.getBody() ;
    }
}
