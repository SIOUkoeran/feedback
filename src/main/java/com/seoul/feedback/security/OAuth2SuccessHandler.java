package com.seoul.feedback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String targetUri = "http://3.34.88.141/project";
//        addSameSiteOnCookie(response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",  "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization, JSESSIONID");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.sendRedirect(targetUri);
    }

    private void addSameSiteOnCookie(HttpServletResponse response) {
        String header = response.getHeader(HttpHeaders.SET_COOKIE);
        response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=None"));
        response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=None"));
    }
}
