package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class TokenExpiredExcpetion extends CustomException {

    public TokenExpiredExcpetion(){
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
