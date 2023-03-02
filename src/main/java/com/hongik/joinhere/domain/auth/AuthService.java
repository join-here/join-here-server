package com.hongik.joinhere.domain.auth;

import com.hongik.joinhere.dto.token.TokenRequest;
import com.hongik.joinhere.dto.token.TokenResponse;
import com.hongik.joinhere.dto.user.CreateUserRequest;
import com.hongik.joinhere.dto.user.LoginUserRequest;
import com.hongik.joinhere.domain.user.entity.Authority;
import com.hongik.joinhere.entity.RefreshToken;
import com.hongik.joinhere.domain.auth.jwt.TokenProvider;
import com.hongik.joinhere.domain.user.entity.User;
import com.hongik.joinhere.repository.RefreshTokenRepository;
import com.hongik.joinhere.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public void signup(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .birthday(request.getBirthday())
                .phone(request.getPhone())
                .authority(Authority.ROLE_USER)
                .build();
        userRepository.save(user);
    }

    public TokenResponse login(LoginUserRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenResponse response = tokenProvider.generateToken(authentication, userRepository.findByUsername(request.getUsername()).get());
        RefreshToken refreshToken = RefreshToken.builder()
                .id(authentication.getName())
                .value(response.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return response;
    }

    public TokenResponse reissue(TokenRequest request) {
        if (!tokenProvider.validateToken(request.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
        Authentication authentication = tokenProvider.getAuthentication(request.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        if (!refreshToken.getValue().equals(request.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
        TokenResponse response = tokenProvider.generateToken(authentication, userRepository.findById(Long.valueOf(authentication.getName())).get());
        RefreshToken newRefreshToken = refreshToken.updateValue(response.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        return response;
    }
}