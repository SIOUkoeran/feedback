package com.seoul.feedback.service.session;


import com.seoul.feedback.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface OAuth2SessionService {

    public User findBySessionUser(HttpSession httpSession);
}
