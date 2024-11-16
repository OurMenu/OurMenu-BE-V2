package com.ourmenu.backend.global.exception;

public class ErrorResponse {

    private String errorCode;

    private String errorMessage;

    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
