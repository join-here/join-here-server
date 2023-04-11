package com.hongik.joinhere.domain.auth.security;

import com.hongik.joinhere.global.error.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hongik.joinhere.global.error.FilterExceptionHandler.*;

/**
 * 유효한 자격증명을 제공하지 않고 접근하려 할때 401
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        setResponse(response, ErrorCode.UNAUTHORIZED_ACCESS);
    }
}