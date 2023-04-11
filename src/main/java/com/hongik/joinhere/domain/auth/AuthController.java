package com.hongik.joinhere.domain.auth;

import com.hongik.joinhere.domain.auth.dto.request.TokenRequest;
import com.hongik.joinhere.domain.auth.dto.response.TokenResponse;
import com.hongik.joinhere.domain.member.dto.CreateMemberRequest;
import com.hongik.joinhere.domain.member.dto.LoginMemberRequest;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public CommonResponse<?> signup(@RequestBody CreateMemberRequest request) {
        authService.signup(request);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }

    @PostMapping("/login")
    public CommonResponse<TokenResponse> login(@RequestBody LoginMemberRequest request) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), authService.login(request));
    }

    @PostMapping("/reissue")
    public CommonResponse<TokenResponse> reissue(@RequestBody TokenRequest request) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), authService.reissue(request));
    }
}