package com.seoul.feedback.controller;

import com.seoul.feedback.dto.Token;
import com.seoul.feedback.dto.response.UserResponse;
import com.seoul.feedback.security.SessionUser;
import com.seoul.feedback.service.RegisterService;
import com.seoul.feedback.service.UserService;
import com.seoul.feedback.service.session.OAuth2SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class LoginController {


    @Value("${42seoul.id}")
    private String clientId;
    @Value("${42seoul.secret}")
    private String secret;
    @Value("${42seoul.redirect-uri}")
    private String redirectUri;

    private final RegisterService registerService;
    private final UserService userService;
    private final HttpSession httpSession;
    private final OAuth2SessionService oAuth2SessionService;

    @Autowired
    public LoginController(RegisterService registerService, UserService userService, HttpSession httpSession,  OAuth2SessionService oAuth2SessionService) {
        this.registerService = registerService;
        this.userService = userService;
        this.oAuth2SessionService =  oAuth2SessionService;
        this.httpSession = httpSession;
    }


    @GetMapping("/login")
    public ResponseEntity cadetLogin(HttpSession session) throws IOException {
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
@GetMapping("/user/login")
public String getLoginAccess(HttpSession httpSession){
    SessionUser user = (SessionUser) httpSession.getAttribute("user");
    return user.getLogin().toString();
}
    @GetMapping("/user/login/success")
    public String success(Token token){
        return token.getAccess_token();
    }

}
