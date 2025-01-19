package com.ourmenu.backend.domain.search.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class ExceededDailyQuotaException extends CustomException {

    public ExceededDailyQuotaException() {
        super(ErrorCode.EXCEEDED_DAILY_QUOTA);
    }
}
