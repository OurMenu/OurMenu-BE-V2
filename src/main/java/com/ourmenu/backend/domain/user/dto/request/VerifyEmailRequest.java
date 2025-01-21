package com.ourmenu.backend.domain.user.dto.request;

import lombok.Getter;

@Getter
public class VerifyEmailRequest {
    private String email;
    private String confirmCode;
}
