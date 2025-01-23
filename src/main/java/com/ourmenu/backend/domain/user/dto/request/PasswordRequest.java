package com.ourmenu.backend.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordRequest {
    String password;
    String newPassword;
}
