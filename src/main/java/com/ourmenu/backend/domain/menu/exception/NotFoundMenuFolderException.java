package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class NotFoundMenuFolderException extends CustomException {
    public NotFoundMenuFolderException() {
        super("찾을 수 없는 메뉴판입니다", ErrorCode.NOT_FOUND_RESOURCE);
    }
}
