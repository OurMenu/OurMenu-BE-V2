package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class S3DeleteFailureException extends CustomException {
    public S3DeleteFailureException() {
        super(ErrorCode.UPLOAD_FAILURE);
    }
}
