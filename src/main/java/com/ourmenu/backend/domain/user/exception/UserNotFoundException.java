package com.ourmenu.backend.domain.user.exception;


import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
