package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class NotMatchTokenException extends CustomException {

    public NotMatchTokenException(){
        super(ErrorCode.NOT_MATCH_TOKEN);
    }
}
