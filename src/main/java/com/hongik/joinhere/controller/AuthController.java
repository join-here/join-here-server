package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.token.TokenRequest;
import com.hongik.joinhere.dto.token.TokenResponse;
import com.hongik.joinhere.dto.user.CreateUserRequest;
import com.hongik.joinhere.dto.user.LoginUserRequest;
import com.hongik.joinhere.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody CreateUserRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginUserRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(authService.reissue(request));
    }
}