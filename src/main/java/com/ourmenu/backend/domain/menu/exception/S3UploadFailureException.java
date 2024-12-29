package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class S3UploadFailureException extends CustomException {
    public S3UploadFailureException() {
        super(ErrorCode.UPLOAD_FAILURE);
    }
}
