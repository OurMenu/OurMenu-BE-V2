package com.ourmenu.backend.domain.user.dto.request;

import lombok.Getter;

@Getter
public class PasswordRequest {
    String password;
    String newPassword;
}
