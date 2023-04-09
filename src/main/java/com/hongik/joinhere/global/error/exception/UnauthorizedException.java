package com.hongik.joinhere.global.error.exception;

import com.hongik.joinhere.global.error.ErrorCode;
import lombok.Getter;

/**
 * status : 401
 */
@Getter
public class UnauthorizedException extends BaseException {

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS, ErrorCode.UNAUTHORIZED_ACCESS.getMessage());
    }

    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED_ACCESS, message);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }
}