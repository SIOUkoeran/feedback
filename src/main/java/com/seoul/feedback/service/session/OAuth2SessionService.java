package com.seoul.feedback.service.session;


import com.seoul.feedback.entity.User;
import javax.servlet.http.HttpSession;


public interface OAuth2SessionService {

    User findBySessionUser(HttpSession httpSession);
}
