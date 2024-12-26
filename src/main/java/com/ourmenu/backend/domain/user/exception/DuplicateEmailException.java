package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class DuplicateEmailException extends CustomException {

    public DuplicateEmailException(){
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
