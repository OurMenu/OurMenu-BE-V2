package com.ourmenu.backend.domain.user.dto.request;

import lombok.Getter;

@Getter
public class SignInRequest {
    private String email;
    private String password;
    private String signInType;
}
