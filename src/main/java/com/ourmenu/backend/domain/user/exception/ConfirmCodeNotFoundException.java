package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class ConfirmCodeNotFoundException extends CustomException {

    public ConfirmCodeNotFoundException(){
        super(ErrorCode.CONFIRM_CODE_NOT_FOUND);
    }
}
