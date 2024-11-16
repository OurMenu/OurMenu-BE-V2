package com.ourmenu.backend.global.exception;

import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleException(RuntimeException e) {
        return handleException(e, ErrorResponse.of(ErrorCode.INTERNAL_SERVER));
    }

    @ExceptionHandler(CustomException.class)
    public ApiResponse<?> handleCustomException(CustomException e) {
        return handleException(e, ErrorResponse.of(e.getMessage(), e.getErrorCode()));
    }

    public ApiResponse<?> handleException(Exception e, ErrorResponse errorResponse) {
        return ApiUtil.error(errorResponse);
    }
}
