package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class NotMatchConfirmCodeException extends CustomException {

    public NotMatchConfirmCodeException(){
        super(ErrorCode.NOT_MATCH_CONFIRM_CODE);
    }
}
