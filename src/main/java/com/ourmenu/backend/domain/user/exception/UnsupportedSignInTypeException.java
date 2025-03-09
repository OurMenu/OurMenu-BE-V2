package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class UnsupportedSignInTypeException extends CustomException {

    public UnsupportedSignInTypeException() {
        super(ErrorCode.UNSUPPORTED_SIGN_IN_TYPE);
    }
}
