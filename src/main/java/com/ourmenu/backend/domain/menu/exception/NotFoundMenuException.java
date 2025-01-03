package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class NotFoundMenuException extends CustomException {
    public NotFoundMenuException() {
        super(ErrorCode.NOT_FOUND_MENU);
    }
}
