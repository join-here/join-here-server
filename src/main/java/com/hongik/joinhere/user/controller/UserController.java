package com.hongik.joinhere.user.controller;

import com.hongik.joinhere.user.UserService;
import com.hongik.joinhere.user.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody CreateUserRequest request) {
        userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    
}
