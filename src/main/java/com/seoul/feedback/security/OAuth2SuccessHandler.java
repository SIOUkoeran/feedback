package com.seoul.feedback.security;

import com.seoul.feedback.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String targetUri = "http://3.34.88.141/cadet";
        String adminTargetUri = "http://3.34.88.141/project";
        addSameSiteOnCookie(response);
        SessionUser sessionUser =(SessionUser) request.getSession(false).getAttribute("user");
        if (sessionUser.getRole() == Role.STUDENT){
            response.sendRedirect(targetUri);
        }
        else {
            response.sendRedirect(adminTargetUri);
        }
    }

    private void addSameSiteOnCookie(HttpServletResponse response) {
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;
        for (String header : headers) {
            if (firstHeader) {
                response.setHeader(HttpHeaders.SET_COOKIE,
                        String.format("%s; Secure; %s", header, "SameSite=None"));
                firstHeader = false;
                continue;
            }
            response.addHeader(HttpHeaders.SET_COOKIE,
                    String.format("%s; Secure; %s", header, "SameSite=None"));
        }
    }
}
