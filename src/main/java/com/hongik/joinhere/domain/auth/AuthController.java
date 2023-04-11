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
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<?> signup(@RequestBody CreateMemberRequest request) {
        authService.signup(request);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }

    @PostMapping("/login")
    public CommonResponse<TokenResponse> login(@RequestBody LoginMemberRequest request) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), authService.login(request));
    }

    @PostMapping("/logout")
    public CommonResponse<?> logout() {
        authService.logout();
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<TokenResponse> reissue(@RequestBody TokenRequest request) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), authService.reissue(request));
    }
}