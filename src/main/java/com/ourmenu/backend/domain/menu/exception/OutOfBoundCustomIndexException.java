package com.ourmenu.backend.domain.menu.exception;

import com.ourmenu.backend.global.exception.CustomException;
import com.ourmenu.backend.global.exception.ErrorCode;

public class OutOfBoundCustomIndexException extends CustomException {
    public OutOfBoundCustomIndexException() {
        super("현재 메뉴판이 가지고 있는 최대 인덱스를 벗어납니다", ErrorCode.MENU_INTERNAL_SERVER);
    }
}
