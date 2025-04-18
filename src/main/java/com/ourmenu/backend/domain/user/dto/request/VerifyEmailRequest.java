package com.ourmenu.backend.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerifyEmailRequest {
    private String email;
    private String confirmCode;
}
