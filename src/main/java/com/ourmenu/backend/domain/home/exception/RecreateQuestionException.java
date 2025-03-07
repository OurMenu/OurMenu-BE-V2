package com.ourmenu.backend.domain.home.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class RecreateQuestionException extends CustomException {

    public RecreateQuestionException() {
        super(ErrorCode.RECREATE_QUESTION);
    }
}
