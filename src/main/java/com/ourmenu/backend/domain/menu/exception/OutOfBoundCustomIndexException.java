package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class OutOfBoundCustomIndexException extends CustomException {
    public OutOfBoundCustomIndexException() {
        super(ErrorCode.OUT_OF_BOUND_CUSTOM_INDEX);
    }
}
