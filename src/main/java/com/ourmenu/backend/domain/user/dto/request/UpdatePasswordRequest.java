package com.ourmenu.backend.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UpdatePasswordRequest {
    String password;
    String newPassword;
}
