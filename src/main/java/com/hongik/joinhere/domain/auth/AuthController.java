package com.hongik.joinhere.domain.auth;

import com.hongik.joinhere.domain.auth.dto.request.TokenRequest;
import com.hongik.joinhere.domain.auth.dto.response.TokenResponse;
import com.hongik.joinhere.dto.user.CreateUserRequest;
import com.hongik.joinhere.dto.user.LoginUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> reissue(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(authService.reissue(request));
    }
}