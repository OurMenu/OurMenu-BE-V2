package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class ForbiddenMenuFolderException extends CustomException {
    public ForbiddenMenuFolderException() {
        super(ErrorCode.FORBIDDEN_MENU_FOLDER);
    }
}
