package com.hongik.joinhere.domain.auth;

import com.hongik.joinhere.domain.auth.dto.request.TokenRequest;
import com.hongik.joinhere.domain.auth.dto.response.TokenResponse;
import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.member.dto.CreateMemberRequest;
import com.hongik.joinhere.domain.member.dto.LoginMemberRequest;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.domain.member.repository.MemberRepository;
import com.hongik.joinhere.domain.auth.entity.RefreshToken;
import com.hongik.joinhere.domain.auth.jwt.TokenProvider;
import com.hongik.joinhere.domain.auth.repository.RefreshTokenRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public void signup(CreateMemberRequest request) {
        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException(ErrorCode.DUPLICATE_MEMBER);
        }
        Member member = request.toEntity(passwordEncoder);
        memberRepository.save(member);
    }

    public TokenResponse login(LoginMemberRequest request) {
        // Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        // 실제로 검증(사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponse response = tokenProvider.generateToken(authentication, memberRepository.findByUsername(request.getUsername()).get());
        RefreshToken refreshToken = RefreshToken.builder()
                .id(authentication.getName())
                .value(response.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return response;
    }

    public void logout() {
        RefreshToken refreshToken = refreshTokenRepository.findById(SecurityUtil.getCurrentMemberId().toString())
                .orElseThrow(() -> new BadRequestException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        refreshTokenRepository.delete(refreshToken);
    }

    public TokenResponse reissue(TokenRequest request) {
        // Refresh Token 검증
        if (!tokenProvider.validateToken(request.getRefreshToken())) {
            throw new BadRequestException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(request.getAccessToken());

        // 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new BadRequestException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        // Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(request.getRefreshToken())) {
            throw new BadRequestException(ErrorCode.MISMATCH_REFRESH_TOKEN);
        }

        // 새로운 토큰 생성
        TokenResponse response = tokenProvider.generateToken(authentication, memberRepository.findById(Long.valueOf(authentication.getName())).get());
        RefreshToken newRefreshToken = refreshToken.updateValue(response.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        return response;
    }
}