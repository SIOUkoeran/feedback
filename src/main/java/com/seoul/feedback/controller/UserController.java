package com.seoul.feedback.controller;

import com.seoul.feedback.dto.response.RegisterResponse;
import com.seoul.feedback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping(value = "/users/{userId}/registers")
    public List<RegisterResponse> findRegistersById (@PathVariable  Long userId) {
        return userService.findByUserId(userId);
    }

    @GetMapping(value = "/project/{projectId}/user/{userId}/feedback-list")
    public ResponseEntity getUserListByProjectId (@PathVariable(name ="projectId") Long projectId,
                                                  @PathVariable(name="userId") Long userId){
        return ResponseEntity.ok().body(this.userService.getFeedbackListByProjectId(projectId, userId));
    }
}

