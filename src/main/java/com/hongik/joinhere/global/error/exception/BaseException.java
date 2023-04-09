package com.hongik.joinhere.global.error.exception;

import com.hongik.joinhere.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 비즈니스 로직 예외 처리용 부모 객체입니다.
 */
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
}
