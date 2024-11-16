package com.ourmenu.backend.global.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(int status, String code, String message) {

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
