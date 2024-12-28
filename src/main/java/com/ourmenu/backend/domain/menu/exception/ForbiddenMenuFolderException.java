package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class ForbiddenMenuFolderException extends CustomException {
    public ForbiddenMenuFolderException() {
        super("소유하고 있는 메뉴판이 아닙니다", ErrorCode.FORBIDDEN_RESOURCE);
    }
}
