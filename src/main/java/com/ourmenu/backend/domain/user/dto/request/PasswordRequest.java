package com.ourmenu.backend.domain.user.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordRequest {

    @NotBlank
    String password;

    @NotBlank
    String newPassword;
}
