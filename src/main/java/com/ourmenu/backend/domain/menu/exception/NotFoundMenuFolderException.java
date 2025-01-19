package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class NotFoundMenuFolderException extends CustomException {
    public NotFoundMenuFolderException() {
        super(ErrorCode.NOT_FOUND_MENU_FOLDER);
    }
}
