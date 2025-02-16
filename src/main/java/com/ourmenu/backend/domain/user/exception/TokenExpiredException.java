package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class TokenExpiredException extends CustomException {

    public TokenExpiredException(){
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
