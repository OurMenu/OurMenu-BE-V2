package com.ourmenu.backend.domain.home.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class NotFoundQuestionException extends CustomException {
    public NotFoundQuestionException() {
        super(ErrorCode.NOT_FOUND_QUESTION);
    }
}
