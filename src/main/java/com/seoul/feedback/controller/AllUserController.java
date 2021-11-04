package com.seoul.feedback.controller;

import com.seoul.feedback.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
//@RequiredArgsConstructor
public class AllUserController {

    //private final RestTemplate restTemplate;
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


    public AllUserController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.intra.42.fr/v2").build();
    }
    private String requestQuery =
            "grant_type=" + "authorization_code" +
            "&" + "code=" + userCode +
            "&" + "client_id=" + clientId +
            "&" + "client_secret=" + clientSecret +
            "&" + "redirect_uri=" + redirectUri +
            "&" + "scope=" + "public";

//    private String getAccessToken() {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        ResponseEntity<Token> tokenResponseEntity = restTemplate.exchange(
//                "https://api.intra.42.fr/oauth/token?" + requestQuery,
//                HttpMethod.POST,
//                new HttpEntity(httpHeaders),
//                Token.class);
//
//        return tokenResponseEntity.getBody().getAccess_token();
//    }

//    private List<User> sendApiRequest(String accessToken) {
//        // 헤더
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Authorization", accessToken);
//
//        // api 요청
//        int pageNum = 0;
//        List<User> userList = new ArrayList<>();
//
//        while (true) {
//            String url = "https://api.intra.42.fr/v2/campus/29/users?page[size]=100&page[number]=" + pageNum;
//            ResponseEntity<List<User>> response = restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    new HttpEntity(httpHeaders),
//                    new ParameterizedTypeReference<List<User>>() {
//                    }
//            );
//            List<User> currList = response.getBody();
//            if (currList == null) {
//                break;
//            }
//            userList.addAll(currList);
//            pageNum++;
//        }
//        return userList;
//    }


    public Mono<List<User>> someRestCall(String accessToken) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/campus/29/users")
                        .queryParam("page[size]", "100")
                        .build())
                .header("Authorization", accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {});
    }

    @RequestMapping("/user")
    public List<User> getAllUsers() {
        //String accessToken = getAccessToken();
        //List<User> userList = sendApiRequest(accessToken);
        //return userList;
        Mono<List<User>> userMono = someRestCall(accessToken);
        return userMono.block();
    }
}
