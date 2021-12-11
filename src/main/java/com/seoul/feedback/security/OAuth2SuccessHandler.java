package com.seoul.feedback.security;

import com.seoul.feedback.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private HttpSession httpSession;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String sessionId= request.getSession(false).getId();
        String targetUri = "localhost:3000";

        Cookie cookie = new Cookie("JSESSIONID", sessionId);
        cookie.setPath("/");
        cookie.setMaxAge(300);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        getRedirectStrategy().sendRedirect(request, response, targetUri);
    }
}
