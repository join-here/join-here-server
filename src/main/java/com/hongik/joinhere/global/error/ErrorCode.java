package com.hongik.joinhere.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* Common */
    INVALID_INPUT_VALUE(BAD_REQUEST, "C001", "Invalid Input Value"),
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "C000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "C001", "잘못된 요청입니다."),
    _METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "C003", "지원하지 않는 Http Method 입니다."),


    /* Auth */
    DUPLICATE_MEMBER(BAD_REQUEST, "AUTH000", "이미 존재하는 ID 입니다."),
    LOGIN_FAILED(UNAUTHORIZED, "AUTH001", "로그인에 실패했습니다"),
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "AUTH002", "권한이 없습니다."),
    FORBIDDEN_USER(FORBIDDEN, "AUTH003", "권한이 없는 유저입니다"),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "AUTH004", "Refresh Token이 유효하지 않습니다"),
    INVALID_TOKEN(BAD_REQUEST, "AUTH005", "유효하지 않은 토큰입니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "AUTH006", "로그아웃 된 사용자입니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "AUTH007", "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "AUTH008", "권한 정보가 없는 토큰입니다"),

    /* Member */
    MEMBER_NOT_FOUND(BAD_REQUEST, "Member000", "해당 회원이 존재하지 않습니다."),

    /* Club */
    DUPLICATE_CLUB(BAD_REQUEST, "CLUB000", "이미 존재하는 동아리 이름입니다."),
    CLUB_NOT_FOUND(BAD_REQUEST, "CLUB001", "해당 동아리가 존재하지 않습니다."),

    /* Announcement */
    ANNOUNCEMENT_NOT_INFORM(BAD_REQUEST, "ANNOUNCEMENT000", "이전 모집공고의 합격 여부를 최종 발표하지 않았습니다."),
    ANNOUNCEMENT_NOT_FOUND(BAD_REQUEST, "ANNOUNCEMENT001", "해당 모집공고가 존재하지 않습니다."),

    /* Belong */
    BELONG_NOT_FOUND(BAD_REQUEST,"BELONG000", "해당 동아리에 소속되어 있지 않습니다."),
    BELONG_FORBIDDEN_MEMBER(FORBIDDEN, "BELONG001", "동아리 관리할 수 있는 권한이 없습니다."),

    /* Application */
    APPLICATION_PERIOD_EXPIRED(BAD_REQUEST, "APPLICATION000", "지원기간이 지났습니다."),
    APPLICATION_NOT_FOUND(BAD_REQUEST, "APPLICATION001", "존재하지 않는 지원입니다."),
    APPLICATION_QUESTION_NOT_FOUND(BAD_REQUEST, "APPLICATION002", "존재하지 않는 지원서 질문입니다."),
    APPLICATION_ANSWER_NOT_FOUND(BAD_REQUEST, "APPLICATION003", "존재하지 않는 지원서 답변입니다."),

    /* S3 */
    S3_SERVER_ERROR(BAD_REQUEST, "S3-000", "Amazon S3가 처리할 수 없는 요청입니다."),
    S3_CONNECTION_ERROR(BAD_REQUEST, "S3-001", "Amazon S3에 연결할 수 없습니다."),

    /* QnA */
    QnA_QUESTION_NOT_FOUND(BAD_REQUEST, "QNA000", "QnA 질문을 찾을 수 없습니다."),
    QnA_ANSWER_NOT_FOUND(BAD_REQUEST, "QNA001", "QnA 답변을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}