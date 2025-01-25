package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class SendCodeFailureException extends CustomException {

    public SendCodeFailureException(){
        super(ErrorCode.SEND_CODE_FAILURE);
    }
}
