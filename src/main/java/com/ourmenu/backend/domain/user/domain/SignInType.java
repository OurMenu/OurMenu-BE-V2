package com.ourmenu.backend.domain.user.domain;

import com.ourmenu.backend.domain.user.exception.UnsupportedSignInTypeException;

public enum SignInType {
    EMAIL, KAKAO;

    public static SignInType convert(String signInType) {
        try {
            return SignInType.valueOf(signInType);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedSignInTypeException();
        }
    }
}
