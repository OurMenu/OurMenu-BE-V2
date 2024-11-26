package com.ourmenu.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "G500", "서버 내부에서 에러가 발생하였습니다"),

    // 유저
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U500", "해당 유저가 존재하지 않습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "U501", "비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U502", "이미 존재하는 이메일입니다.");

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;
}
