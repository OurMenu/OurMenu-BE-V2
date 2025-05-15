package com.ourmenu.backend.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class VerifyEmailRequest {
    private String email;
    private String confirmCode;
}
