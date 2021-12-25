package com.seoul.feedback.controller;

import com.seoul.feedback.dto.response.RegisterResponse;
import com.seoul.feedback.entity.User;
import com.seoul.feedback.service.UserService;
import com.seoul.feedback.service.session.SessionUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SessionUserService sessionUserService;
    private final HttpSession httpSession;

    @GetMapping(value = "/users/{userId}/registers")
    public List<RegisterResponse> findRegistersById (@PathVariable  Long userId) {
        return userService.findByUserId(userId);
    }

    @GetMapping(value = "/project/{projectId}/user/feedback-list")
    public ResponseEntity getUserListByProjectId (@PathVariable(name ="projectId") Long projectId){
        User user = sessionUserService.findBySessionUser(httpSession);
        return ResponseEntity.ok().body(this.userService.getFeedbackListByProjectIdAndUser(projectId, user.getId()));
    }
}

