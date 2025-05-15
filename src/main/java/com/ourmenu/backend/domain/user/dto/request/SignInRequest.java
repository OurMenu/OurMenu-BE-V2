package com.ourmenu.backend.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInRequest {
    private String email;
    private String password;
    private String signInType;
}
