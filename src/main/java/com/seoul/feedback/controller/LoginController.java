package com.seoul.feedback.controller;

import com.seoul.feedback.dto.Token;
import com.seoul.feedback.dto.Token42;
import com.seoul.feedback.service.RegisterService;
import com.seoul.feedback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/api/v1")
public class LoginController {


    @Value("${42.id}")
    private String clientId;
    @Value("${42.secret}")
    private String secret;
    @Value("${42.redirect-uri}")
    private String redirectUri;

    private final RegisterService registerService;
    private final UserService userService;
    private final WebClient webClient;

    @Autowired
    public LoginController(RegisterService registerService, UserService userService, WebClient.Builder webClient) {
        this.registerService = registerService;
        this.userService = userService;
        this.webClient = WebClient.builder().baseUrl("https://api.intra.42.fr/oauth/token?").build();
    }


    @GetMapping("/login")
    public void cadetLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://api.intra.42.fr/oauth/authorize?client_id=13af0d03ad0ae1beba20c1664086ba7b7933f508a37734296cdc7a03f3dcc3b8&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fapi%2Fv1%2Fuser%2Flogin&response_type=code");
    }

    @GetMapping("/user/login")
    public Token getLoginAccess(@RequestParam(name = "code") String code){

        String requestQuery =
                "grant_type=" + "authorization_code" +
                        "&" + "client_id=" + clientId +
                        "&" + "client_secret=" + secret +
                        "&" + "code=" + code +
                        "&" + "redirect_uri=" + redirectUri;

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<Token> responseEntity = restTemplate.exchange("https://api.intra.42.fr/oauth/token?" + requestQuery, HttpMethod.POST,
                new HttpEntity(httpHeaders), Token.class);

        return responseEntity.getBody();
    }

}
