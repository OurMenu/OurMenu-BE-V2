package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class NotFoundMapException extends CustomException {
    public NotFoundMapException() {
        super(ErrorCode.NOT_FOUND_MAP);
    }
}
