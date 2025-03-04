package com.ourmenu.backend.domain.home.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class InvalidAnswerException extends CustomException {
    public InvalidAnswerException() {
        super(ErrorCode.INVALID_ANSWER);
    }
}
