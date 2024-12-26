package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class PasswordNotMatchException extends CustomException {

    public PasswordNotMatchException(){
        super(ErrorCode.PASSWORD_NOT_MATCH);
    }
}
