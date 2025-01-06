package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class ForbiddenMenuException extends CustomException {
    public ForbiddenMenuException() {
        super(ErrorCode.FORBIDDEN_MENU);
    }
}
