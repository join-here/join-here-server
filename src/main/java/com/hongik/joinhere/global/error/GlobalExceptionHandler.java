package com.hongik.joinhere.global.error;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.hongik.joinhere.global.error.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 지원하지 않는 http method 호출시 발생하는 에러
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        return new ResponseEntity<>(ErrorResponse.onFailure(ErrorCode._METHOD_NOT_ALLOWED),
                null, ErrorCode._METHOD_NOT_ALLOWED.getHttpStatus());
    }

    // JSON parse error 일 경우에 발생합니다
    // request 값을 읽을 수 없을 때 발생합니다.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        return new ResponseEntity<>(ErrorResponse.onFailure(ErrorCode._BAD_REQUEST),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());
    }

    // 변수 타입이 맞지 않을 때 발생하는 에러입니다.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException", e);
        return new ResponseEntity<>(ErrorResponse.onFailure(ErrorCode._BAD_REQUEST),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());
    }

    // 변수 타입이 맞지 않을 때 발생하는 에러입니다.
    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.error("EmptyResultDataAccessException", e);
        return new ResponseEntity<>(ErrorResponse.onFailure(ErrorCode._BAD_REQUEST),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());
    }

    // The call was transmitted successfully, but Amazon S3 couldn't process it.
    @ExceptionHandler(AmazonServiceException.class)
    protected ResponseEntity<ErrorResponse> handleAmazonServiceException(AmazonServiceException e) {
        log.error("AmazonServiceException", e);
        return new ResponseEntity<>(ErrorResponse.onFailure(ErrorCode.S3_CONNECTION_ERROR),
                null, ErrorCode.S3_CONNECTION_ERROR.getHttpStatus());
    }

    // thrown when service could not be contacted for a response, or when client is unable to parse the response from service.
    @ExceptionHandler(SdkClientException.class)
    protected ResponseEntity<ErrorResponse> handleSdkClientException(SdkClientException e) {
        log.error("SdkClientException", e);
        return new ResponseEntity<>(ErrorResponse.onFailure(ErrorCode._BAD_REQUEST),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());

    }

    // 로그인 실패했을 때 발생하는 에러 처리
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        log.error("BadCredentialsException", e);
        return new ResponseEntity<>(ErrorResponse.onFailure(ErrorCode.LOGIN_FAILED),
                null, ErrorCode.LOGIN_FAILED.getHttpStatus());
    }

    // 비즈니스 로직 에러 처리
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BaseException baseException) {
        log.error("handleBusinessException", baseException);
        return new ResponseEntity<>(ErrorResponse.onFailure(baseException.getErrorCode()),
                null, baseException.getErrorCode().getHttpStatus());
    }

    // 위에서 따로 처리하지 않은 에러를 모두 처리해줍니다.
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("handleException", exception);
        return new ResponseEntity<>(ErrorResponse.onFailure(ErrorCode._INTERNAL_SERVER_ERROR),
                null, INTERNAL_SERVER_ERROR);
    }
}