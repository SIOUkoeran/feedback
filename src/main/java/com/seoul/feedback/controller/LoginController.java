package com.seoul.feedback.controller;

import com.seoul.feedback.security.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final HttpSession httpSession;
    private final CustomOAuth2UserService oAuth2SessionService;

    @Autowired
    public LoginController(CustomOAuth2UserService oAuth2SessionService, HttpSession httpSession) {
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
    public ResponseEntity cadetLogin(RedirectAttributes redirectAttributes) throws IOException, URISyntaxException {
//        return ResponseEntity.ok(UserResponse.builder()
//                .user(oAuth2SessionService.findBySessionUser(httpSession))
//                .build());
        String targetUri = "http://3.34.88.141/project";
        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);


    }
//    @GetMapping("/user/login")
//    public Token getLoginAccess(@RequestParam(name = "code") String code){
//
//        String requestQuery =
//                "grant_type=" + "authorization_code" +
//                        "&" + "client_id=" + clientId +
//                        "&" + "client_secret=" + secret +
//                        "&" + "code=" + code +
//                        "&" + "redirect_uri=" + redirectUri;
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//        ResponseEntity<Token> responseEntity = restTemplate.exchange("https://api.intra.42.fr/oauth/token?" + requestQuery, HttpMethod.POST,
//                new HttpEntity(httpHeaders), Token.class);
//
//        return responseEntity.getBody() ;
//    }
}
