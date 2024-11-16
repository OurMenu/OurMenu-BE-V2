package com.ourmenu.backend.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "G500", "서버 내부에서 에러가 발생하였습니다");

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
