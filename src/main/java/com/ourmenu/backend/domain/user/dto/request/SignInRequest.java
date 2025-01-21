package com.ourmenu.backend.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String signInType;
}
