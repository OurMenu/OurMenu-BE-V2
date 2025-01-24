package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class InvalidTokenException extends CustomException {

    public InvalidTokenException(){
        super(ErrorCode.INVALID_TOKEN);
    }
}
