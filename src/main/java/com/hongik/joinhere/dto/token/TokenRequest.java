package com.hongik.joinhere.dto.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {

    private String accessToken;
    private String refreshToken;
}
