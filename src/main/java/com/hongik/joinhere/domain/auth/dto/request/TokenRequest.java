package com.hongik.joinhere.domain.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {

    private String accessToken;
    private String refreshToken;
}
