package com.ourmenu.backend.domain.user.exception;


import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class NotFoundUserException extends CustomException {

    public NotFoundUserException(){
        super(ErrorCode.NOT_FOUND_USER);
    }
}
