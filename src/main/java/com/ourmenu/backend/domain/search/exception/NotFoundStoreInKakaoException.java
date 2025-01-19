package com.ourmenu.backend.domain.search.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class NotFoundStoreInKakaoException extends CustomException {
    public NotFoundStoreInKakaoException() {
        super(ErrorCode.NOT_FOUND_STORE_IN_KAKAO);
    }
}
