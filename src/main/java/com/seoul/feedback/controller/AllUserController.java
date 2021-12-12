package com.seoul.feedback.controller;

import com.seoul.feedback.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AllUserController {

    private final WebClient webClient;

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

    @GetMapping("/test")
    public String testController(){
        return "testOk";
    }

    public AllUserController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.intra.42.fr/v2").build();
    }
 /*
    private String requestQuery =
            "grant_type=" + "authorization_code" +
            "&" + "code=" + userCode +
            "&" + "client_id=" + clientId +
            "&" + "client_secret=" + clientSecret +
            "&" + "redirect_uri=" + redirectUri +
            "&" + "scope=" + "public";
*/

/*

    private String getAccessToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<Token> tokenResponseEntity = restTemplate.exchange(
                "https://api.intra.42.fr/oauth/token?" + requestQuery,
                HttpMethod.POST,
                new HttpEntity(httpHeaders),
                Token.class);

        return tokenResponseEntity.getBody().getAccess_token();
    }
*/

    public List<User> getUserbyPage(String accessToken, int page) {
        final int pageSize = 100;
        
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/campus/29/users")
                        .queryParam("page[size]", pageSize)
                        .queryParam("page[number]", page)
                        .build())
                .header("Authorization", accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {})
                .block();
    }


    @RequestMapping("/user")
    public List<User> getAllUsers() {
//       String accessToken = getAccessToken();
        List<User> userList = new ArrayList<>();
        int page = 0;

        while (true) {
            if (page == 6 || page == 26) {
                page++;
            }
            List<User> currList = getUserbyPage(accessToken, page);
            if (currList.isEmpty()) {
                break;
            };
            userList.addAll(currList);
            page++;
        }
        return userList;
    }
}
