package com.seoul.feedback.controller;

import com.seoul.feedback.dto.Token;
import com.seoul.feedback.dto.response.UserResponse;
import com.seoul.feedback.security.SessionUser;
import com.seoul.feedback.service.RegisterService;
import com.seoul.feedback.service.UserService;
import com.seoul.feedback.service.session.OAuth2SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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


    @GetMapping("/login")
    public ResponseEntity cadetLogin() throws IOException {
        return ResponseEntity.ok(UserResponse.builder()
                .user(oAuth2SessionService.findBySessionUser(httpSession))
                .build());
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
