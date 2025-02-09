package com.ourmenu.backend.domain.user.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class InvalidMealTimeCountException extends CustomException {

    public InvalidMealTimeCountException(){
        super(ErrorCode.INVALID_MEAL_TIME_COUNT);
    }
}
